USE bdInmobiliaria;
GO

CREATE PROCEDURE spRegisterNewProperty
    -- Propiedad
    @IdAgent INT,
    @Price DECIMAL(10, 2),
    @IdClient INT,
    @Image VARBINARY(MAX),
    -- Propiedad Detalles
    @IdPropertyType INT,
    @NumberOfFloors INT,
    @TerrainArea DECIMAL(10, 2),
    @BuiltArea DECIMAL(10, 2),
    @AmountRooms INT,
    @AmountBathrooms INT,
    @YearBuilt DATE,
    @AdditionalInformation VARCHAR(200),
    @IdServiceType INT,
    -- Tipo Propiedad
    @PropertyTypeName VARCHAR(100),
    @PropertyTypeDescription VARCHAR(100),
    -- Tipo Servicio
    @NameServiceType VARCHAR(100)
AS
BEGIN
    SET NOCOUNT ON;
    DECLARE @IdProperty INT;
    DECLARE @IdImage INT;

    BEGIN TRY
        BEGIN TRANSACTION;

        -- Verificar si el cliente existe
        IF NOT EXISTS (SELECT 1 FROM tbClient WHERE IdClient = @IdClient)
        BEGIN
            RAISERROR('Client does not exist.', 16, 1);
            RETURN;
        END

        -- Verificar si el agente existe
        IF NOT EXISTS (SELECT 1 FROM tbRealStateAgent WHERE IdRealStateAgent = @IdAgent)
        BEGIN
            RAISERROR('Agent does not exist.', 16, 1);
            RETURN;
        END

        -- Verificar si el tipo de servicio existe, si no, se crea uno nuevo
        IF NOT EXISTS (SELECT 1 FROM tbServiceType WHERE IdServiceType = @IdServiceType)
        BEGIN
            DECLARE @NewServiceTypeID INT;
            EXEC spCreateServiceType @NameServiceType = @NameServiceType, @NewServiceTypeID = @NewServiceTypeID OUTPUT;

            -- Verificar si se insertó correctamente
            IF @NewServiceTypeID IS NULL
            BEGIN
                RAISERROR('Failed to create or retrieve Service Type.', 16, 1);
                RETURN;
            END
            ELSE
            BEGIN
                SET @IdServiceType = @NewServiceTypeID;
            END
        END

        -- Verificar si el tipo de propiedad existe, si no, se crea uno nuevo
        IF NOT EXISTS (SELECT 1 FROM tbPropertyType WHERE IdPropertyType = @IdPropertyType)
        BEGIN
            DECLARE @NewPropertyTypeID INT;
            EXEC spCreatePropertyType @PropertyTypeName = @PropertyTypeName, @PropertyTypeDescription = @PropertyTypeDescription, @NewPropertyTypeID = @NewPropertyTypeID OUTPUT;

            -- Verificar si se insertó correctamente
            IF @NewPropertyTypeID IS NULL
            BEGIN
                RAISERROR('Failed to create or retrieve Property Type.', 16, 1);
                RETURN;
            END
            ELSE
            BEGIN
                SET @IdPropertyType = @NewPropertyTypeID;
            END
        END

        -- Insertar la imagen en la tabla
        DECLARE @tmp DATE = GETDATE();
        EXEC spCreateImage @Image = @Image, @RegistrationDate = @tmp, @NewImageID = @IdImage OUTPUT;

        -- Verificar si se insertó correctamente la imagen
        IF @IdImage IS NULL
        BEGIN
            RAISERROR('Failed to insert image.', 16, 1);
            RETURN;
        END

        -- Insertar la propiedad 
        INSERT INTO tbProperty (IdRealStateAgent, Price, IdClient, IdImage, Status)
        VALUES (@IdAgent, @Price, @IdClient, @IdImage, 1);

        -- Obtener el id de la propiedad recién insertada
        SET @IdProperty = SCOPE_IDENTITY();

        -- Verificar si se insertó correctamente la propiedad
        IF @IdProperty IS NULL
        BEGIN
            RAISERROR('Failed to insert property.', 16, 1);
            RETURN;
        END

        -- Verificar si los detalles de la propiedad ya existen
        IF NOT EXISTS (SELECT 1 FROM tbPropertyDetail WHERE IdProperty = @IdProperty)
        BEGIN
            -- Insertar los detalles de la propiedad
			DECLARE @NewPropertyDetailID INT;
            EXEC spCreatePropertyDetail 
				@IdPropertyType, @NumberOfFloors, @TerrainArea, @BuiltArea, @AmountRooms, 
				@AmountBathrooms, @YearBuilt, @AdditionalInformation, @IdServiceType, @IdProperty, @NewPropertyDetailID OUTPUT;
        END

		-- Actualizar el IdProperty en tbPropertyDetail
		BEGIN
			UPDATE tbProperty SET IdPropertyDetail = @NewPropertyDetailID WHERE IdProperty = @IdProperty;
		END

        COMMIT TRANSACTION;
    END TRY
    BEGIN CATCH
        IF @@TRANCOUNT > 0
            ROLLBACK TRANSACTION;

        DECLARE @ErrorMessage NVARCHAR(4000) = ERROR_MESSAGE();
        DECLARE @ErrorSeverity INT = ERROR_SEVERITY();
        DECLARE @ErrorState INT = ERROR_STATE();
        RAISERROR(@ErrorMessage, @ErrorSeverity, @ErrorState);
    END CATCH
END;
GO