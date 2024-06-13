ALTER PROCEDURE spCreateSesion
    @UserName VARCHAR(100),
    @Password VARCHAR(100),
    @IdUser INT OUTPUT
AS
BEGIN
    SET NOCOUNT ON;

    DECLARE @CurrentDate DATE;
    SET @CurrentDate = GETUTCDATE();

    BEGIN TRAN
    BEGIN TRY
        -- Verificar si el usuario existe
        IF NOT EXISTS (SELECT 1 FROM tbUser WHERE UserName = @UserName AND Password = @Password)
        BEGIN
            RAISERROR ('El usuario o la contraseña no existen.', 16, 1);
            RETURN -1;
        END

        -- Obtener el IdUser del usuario
        SELECT @IdUser = IdUser
        FROM tbUser
        WHERE UserName = @UserName AND Password = @Password;

        -- Verificar si existe una sesión activa para el usuario
        IF EXISTS (SELECT 1 FROM tbSesion WHERE IdUser = @IdUser AND Status = 1 AND ExpirationSesionDate IS NULL)
        BEGIN
            -- Cerrar la sesión activa
            UPDATE tbSesion
            SET ExpirationSesionDate = @CurrentDate,
                Status = 0
            WHERE IdUser = @IdUser AND Status = 1 AND ExpirationSesionDate IS NULL;
        END

        -- Crear nueva sesión
        INSERT INTO tbSesion (TokenSesion, RegistrationSesionDate, ActualizationSesionDate, ExpirationSesionDate, IdUser, Status)
        VALUES (NEWID(), @CurrentDate, @CurrentDate, NULL, @IdUser, 1);

        COMMIT;
    END TRY
    BEGIN CATCH
        ROLLBACK;
        -- Manejar el error
        DECLARE @ErrorMessage NVARCHAR(4000) = ERROR_MESSAGE();
        DECLARE @ErrorSeverity INT = ERROR_SEVERITY();
        DECLARE @ErrorState INT = ERROR_STATE();
        RAISERROR(@ErrorMessage, @ErrorSeverity, @ErrorState);
    END CATCH
END
GO

DECLARE @IdUser INT;

EXEC spCreateSesion
    @UserName = 'johndoe',
    @Password = 'securepassword',
    @IdUser = @IdUser OUTPUT;

-- Ver el resultado
SELECT @IdUser AS IdUser;

SELECT * FROM tbUser
SELECT * FROM tbSesion