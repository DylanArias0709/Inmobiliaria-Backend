ALTER PROCEDURE spLogicalDeleteClient
    @IdClient INT
AS
BEGIN
    SET NOCOUNT ON;

    DECLARE @IdUser INT;
    DECLARE @IdPerson INT;
    DECLARE @IdDirection INT;

    BEGIN TRY
        BEGIN TRANSACTION;

        -- Obtener el IdUser, IdPerson y IdDirection asociados al cliente
        SELECT @IdUser = c.IdUser, 
               @IdPerson = p.IdPerson, 
               @IdDirection = d.IdDirection
        FROM tbClient c
        JOIN tbUser u ON c.IdUser = u.IdUser
        JOIN tbPerson p ON u.IdPerson = p.IdPerson
        JOIN tbDirection d ON p.IdDirection = d.IdDirection
        WHERE c.IdClient = @IdClient;

        -- Verificar si el cliente existe
        IF @IdUser IS NULL OR @IdPerson IS NULL OR @IdDirection IS NULL
        BEGIN
            RAISERROR('El cliente no existe.', 16, 1);
            RETURN;
        END

        -- Actualizar el status en tbClient
        UPDATE tbClient
        SET Status = 0
        WHERE IdClient = @IdClient;

        -- Actualizar el status en tbUser
        UPDATE tbUser
        SET Status = 0
        WHERE IdUser = @IdUser;

        -- Actualizar el status en tbPerson
        UPDATE tbPerson
        SET Status = 0
        WHERE IdPerson = @IdPerson;

        -- Actualizar el status en tbEmail
        UPDATE tbEmail
        SET Status = 0
        WHERE IdPerson = @IdPerson;

        -- Actualizar el status en tbPhone
        UPDATE tbPhone
        SET Status = 0
        WHERE IdPerson = @IdPerson;

        -- Actualizar el status en tbDirection
        UPDATE tbDirection
        SET Status = 0
        WHERE IdDirection = @IdDirection;
		-- Retornar el Id del cliente actualizado
        SELECT @IdClient AS IdClient;
        -- Confirmar la transacción
        COMMIT TRANSACTION;
    END TRY
    BEGIN CATCH
        -- Deshacer la transacción en caso de error
        ROLLBACK TRANSACTION;

        -- Manejar el error
        DECLARE @ErrorMessage NVARCHAR(4000) = ERROR_MESSAGE();
        DECLARE @ErrorSeverity INT = ERROR_SEVERITY();
        DECLARE @ErrorState INT = ERROR_STATE();
        RAISERROR(@ErrorMessage, @ErrorSeverity, @ErrorState);
    END CATCH
END;

EXEC [dbo].[spLogicalDeleteClient]
    @IdClient = 1;
