ALTER PROCEDURE spCloseSesion
    @IdUser INT,
    @IdSesion INT OUTPUT
AS
BEGIN
    SET NOCOUNT ON;

    DECLARE @CurrentDate DATE;
    SET @CurrentDate = GETUTCDATE();

    BEGIN TRAN
    BEGIN TRY
        -- Verificar si existe una sesión activa para el usuario
        IF EXISTS (SELECT 1 FROM tbSesion WHERE IdUser = @IdUser AND Status = 1 AND ExpirationSesionDate IS NULL)
        BEGIN
            -- Cerrar la sesión activa y obtener el IdSesion
            UPDATE tbSesion
            SET ExpirationSesionDate = @CurrentDate,
                Status = 0
            OUTPUT INSERTED.IdSesion
            WHERE IdUser = @IdUser AND Status = 1 AND ExpirationSesionDate IS NULL;
        END
        ELSE
        BEGIN
            -- Si no hay sesión activa, retornar -1
			PRINT 'No hay una sesión activa para este usuario'
            SET @IdSesion = -1;
        END

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


DECLARE @IdSesion INT;
EXEC spCloseSesion @IdUser = 1, @IdSesion = @IdSesion OUTPUT;
SELECT @IdSesion AS ClosedSesionId;


SELECT * FROM tbSesion