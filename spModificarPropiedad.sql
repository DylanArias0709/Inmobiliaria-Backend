USE bdInmobiliaria;
GO

CREATE PROCEDURE spUpdateProperty
    -- Propiedad
    @IdProperty INT,
    @IdAgent INT,
    @Price DECIMAL(10, 2),
    @IdClient INT,
    @Image VARBINARY(MAX),
    @IdImage INT,
    -- Propiedad Detalles
    @IdPropertyDetail INT,
    @IdPropertyType INT,
    @NumberOfFloors INT,
    @TerrainArea DECIMAL(10, 2),
    @BuiltArea DECIMAL(10, 2),
    @AmountRooms INT,
    @AmountBathrooms INT,
    @YearBuilt DATE,
    @AdditionalInformation VARCHAR(200),
    @IdServiceType INT,
    @Status TINYINT
AS
BEGIN
    SET NOCOUNT ON;

    BEGIN TRY
        BEGIN TRANSACTION;

        -- Verificar si la propiedad existe
        IF NOT EXISTS (SELECT 1 FROM tbProperty WHERE IdProperty = @IdProperty)
        BEGIN
            RAISERROR('Property does not exist.', 16, 1);
            RETURN;
        END

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

        -- Verificar si el tipo de servicio existe
        IF NOT EXISTS (SELECT 1 FROM tbServiceType WHERE IdServiceType = @IdServiceType)
        BEGIN
            RAISERROR('Service Type does not exist.', 16, 1);
            RETURN;
        END

        -- Verificar si el tipo de propiedad existe
        IF NOT EXISTS (SELECT 1 FROM tbPropertyType WHERE IdPropertyType = @IdPropertyType)
        BEGIN
            RAISERROR('Property Type does not exist.', 16, 1);
            RETURN;
        END

        -- Actualizar la imagen en la tabla
        DECLARE @tmp DATE = GETDATE();
        EXEC spUpdateImage @IdImage = @IdImage, @NewImage = @Image, @NewRegistrationDate = @tmp, @Status = @Status;

        -- Verificar si se actualizó correctamente la imagen
        IF @@ERROR <> 0
        BEGIN
            RAISERROR('Failed to update image.', 16, 1);
            RETURN;
        END

        -- Actualizar la propiedad
        UPDATE tbProperty
        SET IdRealStateAgent = @IdAgent,
            Price = @Price,
            IdClient = @IdClient,
            IdImage = @IdImage,
			IdPropertyDetail = @IdPropertyDetail,
            Status = @Status
        WHERE IdProperty = @IdProperty;

        -- Verificar si se actualizó correctamente la propiedad
        IF @@ERROR <> 0
        BEGIN
            RAISERROR('Failed to update property.', 16, 1);
            RETURN;
        END

        -- Actualizar los detalles de la propiedad
        EXEC spUpdatePropertyDetail 
            @IdPropertyDetail = @IdPropertyDetail,
            @IdPropertyType = @IdPropertyType,
            @NumberOfFloors = @NumberOfFloors,
            @TerrainArea = @TerrainArea,
            @BuiltArea = @BuiltArea,
            @AmountRooms = @AmountRooms,
            @AmountBathrooms = @AmountBathrooms,
            @YearBuilt = @YearBuilt,
            @AdditionalInformation = @AdditionalInformation,
            @IdServiceType = @IdServiceType,
            @IdProperty = @IdProperty,
            @Status = @Status;

        -- Verificar si se actualizó correctamente los detalles de la propiedad
        IF @@ERROR <> 0
        BEGIN
            RAISERROR('Failed to update property details.', 16, 1);
            RETURN;
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

        -- Envía el mensaje de error al backend
        SELECT @ErrorMessage AS ErrorMessage;
    END CATCH
END;
GO