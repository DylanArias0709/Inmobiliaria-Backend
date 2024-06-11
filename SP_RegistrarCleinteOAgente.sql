USE [bdInmobiliaria]
GO
/****** Object:  StoredProcedure [dbo].[spRegisterPerson]    Script Date: 9/6/2024 16:29:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE [dbo].[spRegisterPerson]
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
    @Name = 'Juan',
    @FirstSurname = 'Pérez',
    @SecondSurname = 'González',
    @IdCard = '1234567890',
    @StatusPerson = 1,
    @IdProvince = 1,
    @IdCanton = 1,
    @IdDistrict = 1,
    @AditionalInformation = 'Calle 123',
    @StatusDirection = 1,
    @Email = 'juan@example.com',
    @StatusEmail = 1,
    @PhoneNumber = '12345678',
    @StatusPhone = 1,
    @UserName = 'juanp',
    @Password = 'password123',
    @ActivationToken = 'abc123',
    @VerificationToken = 'xyz789',
    @StatusUser = 1,
    @Budget = 100000,
    @StatusClient = 1,
    @IdRole = 1,
    @PersonType = 'C';


EXEC spRegisterPerson
    @Name = 'María',
    @FirstSurname = 'López',
    @SecondSurname = 'Martínez',
    @IdCard = '0987654321',
    @StatusPerson = 1,
    @IdProvince = 1,
    @IdCanton = 1,
    @IdDistrict = 1,
    @AditionalInformation = 'Avenida 456',
    @StatusDirection = 1,
    @Email = 'maria@example.com',
    @StatusEmail = 1,
    @PhoneNumber = '87654321',
    @StatusPhone = 1,
    @UserName = 'marial',
    @Password = 'password456',
    @ActivationToken = 'def456',
    @VerificationToken = 'uvw123',
    @StatusUser = 1,
    @MaximumBudget = 500000,
    @StatusRealStateAgent = 1,
    @IdRole = 2,
    @PersonType = 'A';

	INSERT INTO tbRole(RoleName, Status) values('Administrador', 1)
	INSERT INTO tbRole(RoleName, Status) values('Colaborador', 1)