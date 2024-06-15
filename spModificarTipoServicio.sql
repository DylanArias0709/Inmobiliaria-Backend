USE bdInmobiliaria;
GO

CREATE PROCEDURE spUpdateServiceType
    @IdServiceType INT,
	@Status TINYINT,
    @NewNameServiceType VARCHAR(100)
AS
BEGIN
    SET NOCOUNT ON;

    BEGIN TRY
        BEGIN TRANSACTION;

        -- Verificar si el tipo de servicio existe
        IF NOT EXISTS (SELECT 1 FROM tbServiceType WHERE IdServiceType = @IdServiceType)
        BEGIN
            RAISERROR('Service Type does not exist.', 16, 1);
            RETURN;
        END

        -- Actualizar el nombre del tipo de servicio
        UPDATE tbServiceType
        SET NameServiceType = @NewNameServiceType,
			Status = @Status
        WHERE IdServiceType = @IdServiceType;

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