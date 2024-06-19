USE [bdInmobiliaria]
GO
/****** Object:  StoredProcedure [dbo].[spUpdateClient]    Script Date: 9/6/2024 16:32:22 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE [dbo].[spUpdateClient]
    @ClientId INT,
    @Name VARCHAR(100),
    @FirstSurname VARCHAR(100),
    @SecondSurname VARCHAR(100),
    @IdCard VARCHAR(100),
    @StatusPerson TINYINT,

    @IdProvince INT,
    @IdCanton INT,
    @IdDistrict INT,
    @AditionalInformation VARCHAR(200),
    @StatusDirection TINYINT,

    @Email VARCHAR(100),
    @StatusEmail TINYINT,

    @PhoneNumber VARCHAR(20),
    @StatusPhone TINYINT,

    @UserName VARCHAR(100),
    @Password VARCHAR(100),
    @ActivationToken VARCHAR(500),
    @VerificationToken VARCHAR(500),
    @StatusUser TINYINT,

    @Budget DECIMAL(18, 2),
    @StatusClient TINYINT,
    @IdRole INT
AS
BEGIN
    SET NOCOUNT ON;

    DECLARE @IdPerson INT;
    DECLARE @IdDirection INT;
    DECLARE @IdUser INT;

    BEGIN TRY
        BEGIN TRANSACTION;

        -- Obtener @IdUser y @IdPerson a partir de @ClientId
        SELECT 
            @IdUser = u.IdUser,
            @IdPerson = p.IdPerson,
            @IdDirection = d.IdDirection
        FROM tbClient c
        JOIN tbUser u ON c.IdUser = u.IdUser
        JOIN tbPerson p ON u.IdPerson = p.IdPerson
        JOIN tbDirection d ON p.IdDirection = d.IdDirection
        WHERE c.IdClient = @ClientId;

        -- Verificar si el IdProvince existe en tbProvince
        IF NOT EXISTS (SELECT 1 FROM tbProvince WHERE IdProvince = @IdProvince)
        BEGIN
            RAISERROR ('El ID de la Provincia no existe.', 16, 1);
            RETURN -1;
        END

        -- Verificar si el IdCanton existe en tbCanton
        IF NOT EXISTS (SELECT 1 FROM tbCanton WHERE IdCanton = @IdCanton)
        BEGIN
            RAISERROR ('El ID del Cantón no existe.', 16, 1);
            RETURN -2;
        END

        -- Verificar si el IdDistrict existe en tbDistrict
        IF NOT EXISTS (SELECT 1 FROM tbDistrict WHERE IdDistrict = @IdDistrict)
        BEGIN
            RAISERROR ('El ID del Distrito no existe.', 16, 1);
            RETURN -3;
        END

        -- Verificar si el IdCard ya existe en otra persona
        IF EXISTS (SELECT 1 FROM tbPerson WHERE IdCard = @IdCard AND IdPerson <> @IdPerson)
        BEGIN
            RAISERROR ('El ID de la cédula ya está registrado con otra persona.', 16, 1);
            RETURN -4;
        END

        -- Verificar si el Email ya está asociado a otra persona
        IF EXISTS (SELECT 1 FROM tbEmail WHERE Email = @Email AND IdPerson <> @IdPerson)
        BEGIN
            RAISERROR ('El Email ya está registrado con otra persona.', 16, 1);
            RETURN -5;
        END

        -- Verificar si el PhoneNumber ya está asociado a otra persona
        IF EXISTS (SELECT 1 FROM tbPhone WHERE PhoneNumber = @PhoneNumber AND IdPerson <> @IdPerson)
        BEGIN
            RAISERROR ('El Número de teléfono ya está registrado con otra persona.', 16, 1);
            RETURN -6;
        END

        -- Verificar si el ActivationToken ya existe en otro usuario
        IF EXISTS (SELECT 1 FROM tbUser WHERE ActivationToken = @ActivationToken AND IdUser <> @IdUser)
        BEGIN
            RAISERROR ('El token de activación ya está registrado con otro usuario.', 16, 1);
            RETURN -7;
        END

        -- Verificar si el VerificationToken ya existe en otro usuario
        IF EXISTS (SELECT 1 FROM tbUser WHERE VerificationToken = @VerificationToken AND IdUser <> @IdUser)
        BEGIN
            RAISERROR ('El token de verificación ya está registrado con otro usuario.', 16, 1);
            RETURN -8;
        END

        -- Verificar si el IdRole existe en tbRole
        IF NOT EXISTS (SELECT 1 FROM tbRole WHERE IdRole = @IdRole)
        BEGIN
            RAISERROR ('El ID de rol no existe.', 16, 1);
            RETURN -9;
        END

		  -- Verificar si el UserName ya está asociado a otro usuario
        IF EXISTS (SELECT 1 FROM tbUser WHERE UserName = @UserName AND IdUser <> @IdUser)
        BEGIN
            RAISERROR ('El nombre de usuario ya está registrado con otro usuario.', 16, 1);
            RETURN -10;
        END

        -- Actualizar la tabla tbDirection
        UPDATE tbDirection
        SET 
            IdProvince = @IdProvince,
            IdCanton = @IdCanton,
            IdDistrict = @IdDistrict,
            AditionalInformation = @AditionalInformation,
            Status = @StatusDirection
        WHERE IdDirection = @IdDirection;

        -- Actualizar la tabla tbPerson
        UPDATE tbPerson
        SET 
            Name = @Name,
            FirstSurname = @FirstSurname,
            SecondSurname = @SecondSurname,
            IdCard = @IdCard,
            Status = @StatusPerson
        WHERE IdPerson = @IdPerson;

        -- Actualizar la tabla tbEmail
        UPDATE tbEmail
        SET 
            Email = @Email,
            Status = @StatusEmail
        WHERE IdPerson = @IdPerson;

        -- Actualizar la tabla tbPhone
        UPDATE tbPhone
        SET 
            PhoneNumber = @PhoneNumber,
            Status = @StatusPhone
        WHERE IdPerson = @IdPerson;

        -- Actualizar la tabla tbUser
        UPDATE tbUser
        SET 
            UserName = @UserName,
            Password = @Password,
            ActivationToken = @ActivationToken,
            VerificationToken = @VerificationToken,
            IdRole = @IdRole,
            Status = @StatusUser
        WHERE IdUser = @IdUser;

        -- Actualizar la tabla tbClient
        UPDATE tbClient
        SET 
            Budget = @Budget,
            Status = @StatusClient
        WHERE IdClient = @ClientId;

        -- Si todo va bien, confirmar la transacción
        COMMIT TRANSACTION;

        -- Retornar el Id del cliente actualizado
        SELECT @ClientId AS IdClient;
    END TRY
    BEGIN CATCH
        -- En caso de error, deshacer la transacción
        ROLLBACK TRANSACTION;

        -- Manejar el error
        DECLARE @ErrorMessage NVARCHAR(4000) = ERROR_MESSAGE();
        DECLARE @ErrorSeverity INT = ERROR_SEVERITY();
        DECLARE @ErrorState INT = ERROR_STATE();
        RAISERROR(@ErrorMessage, @ErrorSeverity, @ErrorState);
    END CATCH
END;

SELECT * FROM tbClient
SELECT * FROM tbUser

EXEC [dbo].[spUpdateClient]
    @ClientId = 1,
    @Name = 'Adam',
    @FirstSurname = 'Acuña',
    @SecondSurname = 'González',
    @IdCard = '118200907',
    @StatusPerson = 1,

    @IdProvince = 1,
    @IdCanton = 1,
    @IdDistrict = 1,
    @AditionalInformation = 'Calle Principal, San José',
    @StatusDirection = 1,

    @Email = 'adam.acuna@example.com',
    @StatusEmail = 1,

    @PhoneNumber = '63133860',
    @StatusPhone = 1,

    @UserName = 'adamacuna',
    @Password = '1234',
    @ActivationToken = 'newactivationtoken123',
    @VerificationToken = 'newverificationtoken123',
    @StatusUser = 1,

    @Budget = 150000.00,
    @StatusClient = 1,
    @IdRole = 1; -- IdRole correspondiente al rol de Cliente


SELECT
    c.Budget,
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
    tbClient c
    INNER JOIN tbUser u ON c.IdUser = u.IdUser
	INNER JOIN tbRole r ON u.IdRole = r.IdRole
    INNER JOIN tbPerson p ON u.IdPerson = p.IdPerson
	INNER JOIN tbEmail e ON p.IdPerson = e.IdPerson
	INNER JOIN tbPhone ph ON p.IdPerson = ph.IdPerson
    INNER JOIN tbDirection d ON p.IdDirection = d.IdDirection
    INNER JOIN tbProvince pr ON d.IdProvince = pr.IdProvince
    INNER JOIN tbCanton ct ON d.IdCanton = ct.IdCanton
    INNER JOIN tbDistrict dt ON d.IdDistrict = dt.IdDistrict

