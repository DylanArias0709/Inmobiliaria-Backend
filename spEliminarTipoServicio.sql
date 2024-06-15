
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE spLogicalDeleteServiceType
	@ServiceTypeID INT
AS
BEGIN
	
	SET NOCOUNT ON;
	 BEGIN TRY
        BEGIN TRANSACTION;
		 -- Verificar si el servicio existe
        IF NOT EXISTS (SELECT 1 FROM tbServiceType WHERE IdServiceType = @ServiceTypeID)
        BEGIN
            RAISERROR('Service Type does not exist.', 16, 1);
            RETURN;
        END
		-- Actualizar el estado a 0 para realizar un borrado lógico
		UPDATE tbServiceType
		SET Status = 0
		WHERE IdServiceType = @ServiceTypeID

		COMMIT TRANSACTION;
    END TRY
	BEGIN CATCH
	-- Verificar si hay una transacción activa antes de intentar hacer un ROLLBACK
        IF @@TRANCOUNT > 0
            ROLLBACK TRANSACTION;
		-- Errores
		DECLARE @ErrorMessage NVARCHAR(4000) = ERROR_MESSAGE();
        DECLARE @ErrorSeverity INT = ERROR_SEVERITY();
        DECLARE @ErrorState INT = ERROR_STATE();
        RAISERROR(@ErrorMessage, @ErrorSeverity, @ErrorState);

        -- Envía el mensaje de error al backend
        SELECT @ErrorMessage AS ErrorMessage;
	END CATCH
END
GO