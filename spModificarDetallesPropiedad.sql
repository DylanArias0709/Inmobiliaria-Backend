USE bdInmobiliaria;
GO

CREATE PROCEDURE spUpdatePropertyDetail
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
	@IdProperty INT,
    @Status TINYINT
AS
BEGIN
    SET NOCOUNT ON;

    BEGIN TRY
        BEGIN TRANSACTION;

        -- Verificar si el detalle de la propiedad existe
        IF NOT EXISTS (SELECT 1 FROM tbPropertyDetail WHERE IdPropertyDetails = @IdPropertyDetail)
        BEGIN
            RAISERROR('Property Detail does not exist.', 16, 1);
            RETURN;
        END

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
		-- Verificar si  la propiedad existe
        IF NOT EXISTS (SELECT 1 FROM tbProperty WHERE IdProperty = @IdProperty)
        BEGIN
            RAISERROR('Property does not exist.', 16, 1);
            RETURN;
        END
        -- Actualizar los detalles de la propiedad
        UPDATE tbPropertyDetail
        SET IdPropertyType = @IdPropertyType,
            NumberOfFloors = @NumberOfFloors,
            TerrainArea = @TerrainArea,
            BuiltArea = @BuiltArea,
            AmountRooms = @AmountRooms,
            AmountBathrooms = @AmountBathrooms,
            YearBuilt = @YearBuilt,
            AditionalInformation = @AdditionalInformation,
            IdServiceType = @IdServiceType,
			IdProperty = @IdProperty,
            Status = @Status
        WHERE IdPropertyDetails = @IdPropertyDetail;

        COMMIT TRANSACTION;
    END TRY
    BEGIN CATCH
        -- Verificar si hay una transacción activa antes de intentar hacer un ROLLBACK
        IF @@TRANCOUNT > 0
            ROLLBACK TRANSACTION;

        -- Capturar y lanzar el error
        DECLARE @ErrorMessage NVARCHAR(4000) = ERROR_MESSAGE();
        DECLARE @ErrorSeverity INT = ERROR_SEVERITY();
        DECLARE @ErrorState INT = ERROR_STATE();
        RAISERROR(@ErrorMessage, @ErrorSeverity, @ErrorState);

        -- Envía el mensaje de error al backend
        SELECT @ErrorMessage AS ErrorMessage;
    END CATCH
END;
GO