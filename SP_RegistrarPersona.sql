ALTER PROCEDURE spRegisterPerson
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

    @Budget DECIMAL(18, 2) = NULL,
    @MaximumBudget DECIMAL(18, 2) = NULL,
    @StatusClient TINYINT = NULL,
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

            @IdProvince = @IdProvince,
            @IdCanton = @IdCanton,
            @IdDistrict = @IdDistrict,
            @AditionalInformation = @AditionalInformation,

            @Email = @Email,

            @PhoneNumber = @PhoneNumber,

            @UserName = @UserName,
            @Password = @Password,
            @ActivationToken = @ActivationToken,
            @VerificationToken = @VerificationToken,

            @Budget = @Budget,
            @IdRole = @IdRole;
    END
    ELSE IF @PersonType = 'A'
    BEGIN
        EXEC spRegisterRealStateAgent 
            @Name = @Name,
            @FirstSurname = @FirstSurname,
            @SecondSurname = @SecondSurname,
            @IdCard = @IdCard,

            @IdProvince = @IdProvince,
            @IdCanton = @IdCanton,
            @IdDistrict = @IdDistrict,
            @AditionalInformation = @AditionalInformation,

            @Email = @Email,

            @PhoneNumber = @PhoneNumber,

            @UserName = @UserName,
            @Password = @Password,
            @ActivationToken = @ActivationToken,
            @VerificationToken = @VerificationToken,

            @MaximumBudget = @MaximumBudget,
            @IdRole = @IdRole;
    END
    ELSE
    BEGIN
        RAISERROR ('Tipo de persona no válido. Use "C" para Cliente o "A" para Agente Inmobiliario.', 16, 1);
    END
END;

EXEC spRegisterPerson 
    @Name = 'Aaron',
    @FirstSurname = 'Matarrita',
    @SecondSurname = 'No sé',
    @IdCard = '5121065023',

    @IdProvince = 1,
    @IdCanton = 1,
    @IdDistrict = 1,
    @AditionalInformation = 'Calle 123, Casa 456',

    @Email = 'aaron.matarrita@example.com',

    @PhoneNumber = '564354654534',

    @UserName = 'aaronmatarrita',
    @Password = '12346541564',
    @ActivationToken = 'activation-token-125',
    @VerificationToken = 'verification-token-125',

    @Budget = 50000.00,
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