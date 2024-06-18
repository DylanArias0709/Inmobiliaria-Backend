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
    @Name = 'Pedro',
    @FirstSurname = 'Caramanga',
    @SecondSurname = 'Rodriguez',
    @IdCard = '546312268',
    @IdProvince = 1,
    @IdCanton = 1,
    @IdDistrict = 1,
    @AditionalInformation = 'Calle 123',
    @Email = 'pedro@example.com',
    @PhoneNumber = '566546530566',
    @UserName = 'pedrocaramanga',
    @Password = 'password1234',
    @ActivationToken = 'abc124',
    @VerificationToken = 'no hay',
    @Budget = 100000,
    @IdRole = 1,
    @PersonType = 'C';


EXEC spRegisterPerson
    @Name = 'María',
    @FirstSurname = 'López',
    @SecondSurname = 'Martínez',
    @IdCard = '0987654321',
    @IdProvince = 1,
    @IdCanton = 1,
    @IdDistrict = 1,
    @AditionalInformation = 'Avenida 456',
    @Email = 'maria@example.com',
    @PhoneNumber = '87654321',
    @UserName = 'marial',
    @Password = 'password456',
    @ActivationToken = 'def456',
    @VerificationToken = 'uvw123',
    @MaximumBudget = 500000,
    @IdRole = 2,
    @PersonType = 'A';

	INSERT INTO tbRole(RoleName, Status) values('Administrador', 1)
	INSERT INTO tbRole(RoleName, Status) values('Colaborador', 1)