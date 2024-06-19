CREATE PROCEDURE spRegisterRealStateAgent
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

    @MaximumBudget DECIMAL(18, 2),
    @StatusRealStateAgent TINYINT,
    @IdRole INT
AS
BEGIN
    SET NOCOUNT ON;

    DECLARE @IdPerson INT;
    DECLARE @IdDirection INT;
    DECLARE @IdUser INT;

    BEGIN TRY
        BEGIN TRANSACTION;

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

        -- Verificar si el IdCard ya existe en tbPerson
        IF EXISTS (SELECT 1 FROM tbPerson WHERE IdCard = @IdCard)
        BEGIN
            RAISERROR ('El ID de la cédula ya existe.', 16, 1);
            RETURN -4;
        END

        -- Verificar si el Email ya está asociado a una persona
        IF EXISTS (SELECT 1 FROM tbEmail WHERE Email = @Email)
        BEGIN
            RAISERROR ('El Email ya está asociado a una persona.', 16, 1);
            RETURN -5;
        END

        -- Verificar si el PhoneNumber ya está asociado a una persona
        IF EXISTS (SELECT 1 FROM tbPhone WHERE PhoneNumber = @PhoneNumber)
        BEGIN
            RAISERROR ('El Numero de telefono ya está asociado a una persona.', 16, 1);
            RETURN -6;
        END

        -- Verificar si el ActivationToken ya existe en tbUser
        IF EXISTS (SELECT 1 FROM tbUser WHERE ActivationToken = @ActivationToken)
        BEGIN
            RAISERROR ('El token de activación ya existe.', 16, 1);
            RETURN -7;
        END

        -- Verificar si el VerificationToken ya existe en tbUser
        IF EXISTS (SELECT 1 FROM tbUser WHERE VerificationToken = @VerificationToken)
        BEGIN
            RAISERROR ('El token de verificación ya existe.', 16, 1);
            RETURN -8;
        END

        -- Verificar si el IdRole existe en tbRole
        IF NOT EXISTS (SELECT 1 FROM tbRole WHERE IdRole = @IdRole)
        BEGIN
            RAISERROR ('El ID de rol no existe.', 16, 1);
            RETURN -9;
        END

        -- Insertar en la tabla tbDirection
        INSERT INTO tbDirection (IdProvince, IdCanton, IdDistrict, AditionalInformation, Status)
        VALUES (@IdProvince, @IdCanton, @IdDistrict, @AditionalInformation, @StatusDirection);

        -- Obtener el IdDirection recién insertado
        SET @IdDirection = SCOPE_IDENTITY();

        -- Insertar en la tabla tbPerson
        INSERT INTO tbPerson (Name, FirstSurname, SecondSurname, IdCard, IdDirection, Status)
        VALUES (@Name, @FirstSurname, @SecondSurname, @IdCard, @IdDirection, @StatusPerson);

        -- Obtener el IdPerson recién insertado
        SET @IdPerson = SCOPE_IDENTITY();

        -- Insertar en la tabla tbEmail
        INSERT INTO tbEmail (IdPerson, Email, Status)
        VALUES (@IdPerson, @Email, @StatusEmail);

        -- Insertar en la tabla tbPhone
        INSERT INTO tbPhone (IdPerson, PhoneNumber, Status)
        VALUES (@IdPerson, @PhoneNumber, @StatusPhone);

        -- Insertar en la tabla tbUser
        INSERT INTO tbUser (UserName, RegistrationDate, Password, ActivationToken, VerificationToken, IdPerson, IdRole, Status)
        VALUES (@UserName, GETUTCDATE(), @Password, @ActivationToken, @VerificationToken, @IdPerson, @IdRole, @StatusUser);

        -- Obtener el IdUser recién insertado
        SET @IdUser = SCOPE_IDENTITY();

        -- Insertar en la tabla tbRealStateAgent
        INSERT INTO tbRealStateAgent (IdUser, MaximumBudget, Status)
        VALUES (@IdUser, @MaximumBudget, @StatusRealStateAgent);

        -- Si todo va bien, confirmar la transacción
        COMMIT TRANSACTION;

        -- Retornar el Id del usuario registrado
        SELECT @IdUser AS IdUser;
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

EXEC spRegisterRealStateAgent 
    @Name = 'Juan',
    @FirstSurname = 'Pérez',
    @SecondSurname = 'Gómez',
    @IdCard = '123456789',
    @StatusPerson = 1,

    @IdProvince = 1,
    @IdCanton = 1,
    @IdDistrict = 1,
    @AditionalInformation = 'Calle 123, Casa 456',
    @StatusDirection = 1,

    @Email = 'juan.perez@example.com',
    @StatusEmail = 1,

    @PhoneNumber = '85466985',
    @StatusPhone = 1,

    @UserName = 'juanperez',
    @Password = 'SecurePassword123',
    @ActivationToken = 'activation-token-123',
    @VerificationToken = 'verification-token-123',
    @StatusUser = 1,

    @MaximumBudget = 100000.00,
    @StatusRealStateAgent = 1,
    @IdRole = 2;


SELECT * FROM tbRealStateAgent