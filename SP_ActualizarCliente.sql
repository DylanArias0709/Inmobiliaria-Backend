ALTER PROCEDURE spUpdateClient
    @ClientId INT,
    @Name VARCHAR(100),
    @FirstSurname VARCHAR(100),
    @SecondSurname VARCHAR(100),
    @IdCard VARCHAR(100),

    @IdProvince INT,
    @IdCanton INT,
    @IdDistrict INT,
    @AditionalInformation VARCHAR(200),

    @Email VARCHAR(100),

    @PhoneNumber VARCHAR(20),

    @UserName VARCHAR(100),
    @Password VARCHAR(100),
    @ActivationToken VARCHAR(500),
    @VerificationToken VARCHAR(500),

    @Budget DECIMAL(18, 2),
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

        -- Actualizar la tabla tbDirection
        UPDATE tbDirection
        SET 
            IdDistrict = @IdDistrict,
            AditionalInformation = @AditionalInformation
        WHERE IdDirection = @IdDirection;

        -- Actualizar la tabla tbPerson
        UPDATE tbPerson
        SET 
            Name = @Name,
            FirstSurname = @FirstSurname,
            SecondSurname = @SecondSurname,
            IdCard = @IdCard
        WHERE IdPerson = @IdPerson;

        -- Actualizar la tabla tbEmail
        UPDATE tbEmail
        SET 
            Email = @Email
        WHERE IdPerson = @IdPerson;

        -- Actualizar la tabla tbPhone
        UPDATE tbPhone
        SET 
            PhoneNumber = @PhoneNumber
        WHERE IdPerson = @IdPerson;

        -- Actualizar la tabla tbUser
        UPDATE tbUser
        SET 
            UserName = @UserName,
            Password = @Password,
            ActivationToken = @ActivationToken,
            VerificationToken = @VerificationToken,
            IdRole = @IdRole
        WHERE IdUser = @IdUser;

        -- Actualizar la tabla tbClient
        UPDATE tbClient
        SET 
            Budget = @Budget
        WHERE IdClient = @ClientId;

        -- Si todo va bien, confirmar la transacción
        COMMIT TRANSACTION;

        -- Retornar el Id del cliente actualizado
        SELECT @ClientId AS IdClient;
    END TRY
    BEGIN CATCH
	SET @ClientId = 0;
	SELECT @ClientId AS IdClient;
        -- En caso de error, deshacer la transacción
        ROLLBACK TRANSACTION;

        -- Manejar el error
        DECLARE @ErrorMessage NVARCHAR(4000) = ERROR_MESSAGE();
        DECLARE @ErrorSeverity INT = ERROR_SEVERITY();
        DECLARE @ErrorState INT = ERROR_STATE();
        RAISERROR(@ErrorMessage, @ErrorSeverity, @ErrorState);
    END CATCH
END;



EXEC spUpdateClient 
    @ClientId = 1, 
    @Name = 'Adam', 
    @FirstSurname = 'Acuña', 
    @SecondSurname = 'González', 
    @IdCard = '123456789', 
    @IdProvince = 9, 
    @IdCanton = 1, 
    @IdDistrict = 1, 
    @AditionalInformation = 'Near the park', 
    @Email = 'adam@gmail.com', 
    @PhoneNumber = '62913160', 
    @UserName = 'adamacuna', 
    @Password = 'newpassword', 
    @ActivationToken = 'activation-token-124', 
    @VerificationToken = 'verification-token-124', 
    @Budget = 1000.00, 
    @IdRole = 1;


SELECT
    c.Budget,
    c.Status AS ClientStatus,
    u.UserName,
	r.RoleName AS RolDeUsuario,
    u.ActivationToken,
    u.VerificationToken,
    u.Status AS UserStatus,
    p.Name AS PersonName,
    p.FirstSurname,
    p.SecondSurname,
    p.IdCard,
	ph.PhoneNumber,
	e.Email,
    p.Status AS PersonStatus
FROM
    tbClient c
    INNER JOIN tbUser u ON c.IdUser = u.IdUser
	INNER JOIN tbRole r ON u.IdRole = r.IdRole
    INNER JOIN tbPerson p ON u.IdPerson = p.IdPerson
	INNER JOIN tbPhone ph ON p.IdPerson = ph.IdPerson
	INNER JOIN tbEmail e ON p.IdPerson = e.IdPerson
