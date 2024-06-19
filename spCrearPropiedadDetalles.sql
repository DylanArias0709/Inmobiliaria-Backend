USE bdInmobiliaria;
GO

CREATE PROCEDURE spCreatePropertyDetail
    @IdPropertyType INT,
    @NumberOfFloors INT,
    @TerrainArea DECIMAL(10, 2),
    @BuiltArea DECIMAL(10, 2),
    @AmountRooms INT,
    @AmountBathrooms INT,
    @YearBuilt DATE,
    @AdditionalInformation VARCHAR(200),
    @IdServiceType INT,
    @IdProperty INT,
    @NewPropertyDetailID INT OUTPUT
AS
BEGIN
    SET NOCOUNT ON;

    BEGIN TRY
        BEGIN TRANSACTION;

        -- Verificar si IdPropertyType existe
        IF NOT EXISTS (SELECT 1 FROM tbPropertyType WHERE IdPropertyType = @IdPropertyType)
        BEGIN
            RAISERROR('Property Type does not exist.', 16, 1);
            RETURN;
        END

        -- Verificar si IdServiceType existe
        IF NOT EXISTS (SELECT 1 FROM tbServiceType WHERE IdServiceType = @IdServiceType)
        BEGIN
            RAISERROR('Service Type does not exist.', 16, 1);
            RETURN;
        END

        -- Verificar si IdProperty existe
        IF NOT EXISTS (SELECT 1 FROM tbProperty WHERE IdProperty = @IdProperty)
        BEGIN
            RAISERROR('Property does not exist.', 16, 1);
            RETURN;
        END

        -- Insertar en la tabla PropiedadDetalle
        INSERT INTO tbPropertyDetail (IdPropertyType, NumberOfFloors, TerrainArea, BuiltArea, AmountRooms, AmountBathrooms, YearBuilt, AditionalInformation, IdServiceType, IdProperty, Status)
        VALUES (@IdPropertyType, @NumberOfFloors, @TerrainArea, @BuiltArea, @AmountRooms, @AmountBathrooms, @YearBuilt, @AdditionalInformation, @IdServiceType, @IdProperty, 1);

        -- Obtener el ID del nuevo detalle de la propiedad
        SET @NewPropertyDetailID = SCOPE_IDENTITY();

        COMMIT TRANSACTION;
    END TRY
    BEGIN CATCH
        ROLLBACK TRANSACTION;

        DECLARE @ErrorMessage NVARCHAR(4000) = ERROR_MESSAGE();
        DECLARE @ErrorSeverity INT = ERROR_SEVERITY();
        DECLARE @ErrorState INT = ERROR_STATE();
        RAISERROR(@ErrorMessage, @ErrorSeverity, @ErrorState);

        -- Env�a el mensaje de error al backend
        SELECT @ErrorMessage AS ErrorMessage;
    END CATCH
END;
GO
