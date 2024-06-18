USE bdInmobiliaria;
GO

CREATE PROCEDURE spCreateImage
    @Image VARBINARY(MAX),
    @RegistrationDate DATE,
    @NewImageID INT OUTPUT
AS
BEGIN
    SET NOCOUNT ON;

    BEGIN TRY
        BEGIN TRANSACTION;

        -- Insertar en la tabla de imágenes
        INSERT INTO tbImage (Image, RegistrationDate, Status)
        VALUES (@Image, @RegistrationDate, 1);

        -- Obtener el ID de la nueva imagen
        SET @NewImageID = SCOPE_IDENTITY();

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
