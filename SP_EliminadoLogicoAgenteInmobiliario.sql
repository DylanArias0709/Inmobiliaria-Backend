CREATE PROCEDURE spLogicalDeleteRealStateAgent
    @RealStateAgentId INT
AS
BEGIN
    SET NOCOUNT ON;

    DECLARE @IdUser INT;
    DECLARE @IdPerson INT;
    DECLARE @IdDirection INT;

    BEGIN TRY
        BEGIN TRANSACTION;

        -- Obtener el IdUser, IdPerson y IdDirection asociados al cliente
        SELECT @IdUser = r.IdUser, 
               @IdPerson = p.IdPerson, 
               @IdDirection = d.IdDirection
        FROM tbRealStateAgent r
        JOIN tbUser u ON r.IdUser = u.IdUser
        JOIN tbPerson p ON u.IdPerson = p.IdPerson
        JOIN tbDirection d ON p.IdDirection = d.IdDirection
        WHERE r.IdRealStateAgent = @RealStateAgentId;

        -- Verificar si el cliente existe
        IF @IdUser IS NULL OR @IdPerson IS NULL OR @IdDirection IS NULL
        BEGIN
            RAISERROR('El cliente no existe.', 16, 1);
            RETURN;
        END

        -- Actualizar el status en tbClient
        UPDATE tbRealStateAgent
        SET Status = 0
        WHERE IdRealStateAgent = @RealStateAgentId;

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
        SELECT @RealStateAgentId AS IdRealStateAgent;
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

EXEC [dbo].[spLogicalDeleteRealStateAgent]
    @RealStateAgentId = 1;

SELECT
    c.MaximumBudget,
    c.Status AS ClientStatus,
    u.UserName,
	r.RoleName,
    u.ActivationToken,
    u.VerificationToken,
    u.Status AS UserStatus,
    p.Name AS PersonName,
    p.FirstSurname,
    p.SecondSurname,
	e.Email,
	ph.PhoneNumber,
    p.IdCard,
    p.Status AS PersonStatus,
    d.AditionalInformation AS ExactDirection,
    d.Status AS DirectionStatus,
    pr.Name AS ProvinceName,
    ct.Name AS CantonName,
    dt.Name AS DistrictName
FROM
    tbRealStateAgent c
    INNER JOIN tbUser u ON c.IdUser = u.IdUser
	INNER JOIN tbRole r ON u.IdRole = r.IdRole
    INNER JOIN tbPerson p ON u.IdPerson = p.IdPerson
	INNER JOIN tbEmail e ON p.IdPerson = e.IdPerson
	INNER JOIN tbPhone ph ON p.IdPerson = ph.IdPerson
    INNER JOIN tbDirection d ON p.IdDirection = d.IdDirection
    INNER JOIN tbProvince pr ON d.IdProvince = pr.IdProvince
    INNER JOIN tbCanton ct ON d.IdCanton = ct.IdCanton
    INNER JOIN tbDistrict dt ON d.IdDistrict = dt.IdDistrict