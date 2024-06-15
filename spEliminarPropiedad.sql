SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE spLogicalDeleteProperty
    @IdProperty INT
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

        -- Actualizar el estado a 0 para realizar un borrado lógico
        UPDATE tbProperty
        SET Status = 0
        WHERE IdProperty = @IdProperty;

		-- Verificar si los detalles existen
        IF NOT EXISTS (SELECT 1 FROM tbPropertyDetail WHERE IdProperty = @IdProperty)
        BEGIN
            RAISERROR('Property Details does not exist.', 16, 1);
            RETURN;
        END

        -- Actualizar el estado a 0 para realizar un borrado lógico en los detalles de la propiedad
        UPDATE tbPropertyDetail
        SET Status = 0
        WHERE IdProperty = @IdProperty;

        -- Actualizar el estado a 0 para realizar un borrado lógico en la imagen relacionada
        UPDATE tbImage
        SET Status = 0
        WHERE IdImage = (SELECT IdImage FROM tbProperty WHERE IdProperty = @IdProperty);

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

EXEC spLogicalDeleteProperty
@IdProperty = 1