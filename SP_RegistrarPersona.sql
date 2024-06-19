CREATE PROCEDURE spRegisterPerson
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

    @Budget DECIMAL(18, 2) = NULL,
    @MaximumBudget DECIMAL(18, 2) = NULL,
    @StatusClient TINYINT = NULL,
    @StatusRealStateAgent TINYINT = NULL,
    @IdRole INT,
    @PersonType CHAR(1) -- 'C' para Cliente, 'A' para Agente Inmobiliario
AS
BEGIN
    IF @PersonType = 'C'
    BEGIN
        EXEC spRegisterClient 
            @Name = @Name,
            @FirstSurname = @FirstSurname,
            @SecondSurname = @SecondSurname,
            @IdCard = @IdCard,
            @StatusPerson = @StatusPerson,

            @IdProvince = @IdProvince,
            @IdCanton = @IdCanton,
            @IdDistrict = @IdDistrict,
            @AditionalInformation = @AditionalInformation,
            @StatusDirection = @StatusDirection,

            @Email = @Email,
            @StatusEmail = @StatusEmail,

            @PhoneNumber = @PhoneNumber,
            @StatusPhone = @StatusPhone,

            @UserName = @UserName,
            @Password = @Password,
            @ActivationToken = @ActivationToken,
            @VerificationToken = @VerificationToken,
            @StatusUser = @StatusUser,

            @Budget = @Budget,
            @StatusClient = @StatusClient,
            @IdRole = @IdRole;
    END
    ELSE IF @PersonType = 'A'
    BEGIN
        EXEC spRegisterRealStateAgent 
            @Name = @Name,
            @FirstSurname = @FirstSurname,
            @SecondSurname = @SecondSurname,
            @IdCard = @IdCard,
            @StatusPerson = @StatusPerson,

            @IdProvince = @IdProvince,
            @IdCanton = @IdCanton,
            @IdDistrict = @IdDistrict,
            @AditionalInformation = @AditionalInformation,
            @StatusDirection = @StatusDirection,

            @Email = @Email,
            @StatusEmail = @StatusEmail,

            @PhoneNumber = @PhoneNumber,
            @StatusPhone = @StatusPhone,

            @UserName = @UserName,
            @Password = @Password,
            @ActivationToken = @ActivationToken,
            @VerificationToken = @VerificationToken,
            @StatusUser = @StatusUser,

            @MaximumBudget = @MaximumBudget,
            @StatusRealStateAgent = @StatusRealStateAgent,
            @IdRole = @IdRole;
    END
    ELSE
    BEGIN
        RAISERROR ('Tipo de persona no válido. Use "C" para Cliente o "A" para Agente Inmobiliario.', 16, 1);
    END
END;

EXEC spRegisterPerson 
    @Name = 'Adam',
    @FirstSurname = 'Acuña',
    @SecondSurname = 'González',
    @IdCard = '118200907',
    @StatusPerson = 1,

    @IdProvince = 1,
    @IdCanton = 1,
    @IdDistrict = 1,
    @AditionalInformation = 'Calle 123, Casa 456',
    @StatusDirection = 1,

    @Email = 'adam.acuna@example.com',
    @StatusEmail = 1,

    @PhoneNumber = '63133860',
    @StatusPhone = 1,

    @UserName = 'adamacuna',
    @Password = 'SecurePassword123',
    @ActivationToken = 'activation-token-124',
    @VerificationToken = 'verification-token-124',
    @StatusUser = 1,

    @Budget = 50000.00,
    @StatusClient = 1,
    @IdRole = 2,
    @PersonType = 'C';

	SELECT * FROM tbClient
	SELECT * FROM tbUser
	SELECT * FROM tbPerson

EXEC spRegisterPerson 
    @Name = 'Maria',
    @FirstSurname = 'López',
    @SecondSurname = 'Martínez',
    @IdCard = '987654321',
    @StatusPerson = 1,

    @IdProvince = 2,
    @IdCanton = 2,
    @IdDistrict = 2,
    @AditionalInformation = 'Avenida 123, Edificio 456',
    @StatusDirection = 1,

    @Email = 'maria.lopez@example.com',
    @StatusEmail = 1,

    @PhoneNumber = '0987654321',
    @StatusPhone = 1,

    @UserName = 'marialopez',
    @Password = 'SecurePassword456',
    @ActivationToken = 'activation-token-456',
    @VerificationToken = 'verification-token-456',
    @StatusUser = 1,

    @MaximumBudget = 200000.00,
    @StatusRealStateAgent = 1,
    @IdRole = 1,
    @PersonType = 'A';

	SELECT * FROM tbRealStateAgent
	SELECT * FROM tbUser
	SELECT * FROM tbPerson