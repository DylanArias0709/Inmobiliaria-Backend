USE bdInmobiliaria;
GO

CREATE PROCEDURE spUpdateImage
    @IdImage INT,
    @NewImage VARBINARY(MAX),
    @NewRegistrationDate DATE,
    @Status TINYINT
AS
BEGIN
    SET NOCOUNT ON;

    BEGIN TRY
        BEGIN TRANSACTION;

        -- Verificar si la imagen existe
        IF NOT EXISTS (SELECT 1 FROM tbImage WHERE IdImage = @IdImage)
        BEGIN
            RAISERROR('Image does not exist.', 16, 1);
            RETURN;
        END

        -- Actualizar los campos de la imagen, solo si se proporcionan
        UPDATE tbImage
        SET Image = @NewImage,
            RegistrationDate = @NewRegistrationDate,
            Status = @Status
        WHERE IdImage = @IdImage;

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