USE [bdInmobiliaria]
GO
/****** Object:  StoredProcedure [dbo].[spLogicalDeleteUser]    Script Date: 12/6/2024 16:55:30 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE [dbo].[spLogicalDeleteUser]
    @IdUser INT
AS
BEGIN
    SET NOCOUNT ON;
    DECLARE @IdPerson INT;
    DECLARE @IdDirection INT;

    BEGIN TRY
        BEGIN TRANSACTION;

        -- Obtener el IdUser, IdPerson y IdDirection asociados al usuario
        SELECT @IdPerson = p.IdPerson, 
               @IdDirection = d.IdDirection
        FROM tbUser u
        JOIN tbPerson p ON u.IdPerson = p.IdPerson
        JOIN tbDirection d ON p.IdDirection = d.IdDirection
        WHERE u.IdUser = @IdUser;

        -- Verificar si el usuario existe
        IF @IdPerson IS NULL OR @IdDirection IS NULL
        BEGIN
            RAISERROR('El usuario no existe.', 16, 1);
            RETURN;
        END
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
        SELECT @IdUser AS IdUser;
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