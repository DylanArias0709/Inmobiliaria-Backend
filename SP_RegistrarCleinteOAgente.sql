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

EXEC [dbo].[spRegisterPerson]
    @Name = 'Juan',
    @FirstSurname = 'Pérez',
    @SecondSurname = 'González',
    @IdCard = '123456789',
    @StatusPerson = 1,

    @IdProvince = 1,
    @IdCanton = 1,
    @IdDistrict = 1,
    @AditionalInformation = 'Calle 123, San José',
    @StatusDirection = 1,

    @Email = 'juan.perez@example.com',
    @StatusEmail = 1,

    @PhoneNumber = '1234567890',
    @StatusPhone = 1,

    @UserName = 'juanperez',
    @Password = 'password123',
    @ActivationToken = 'activationtoken123',
    @VerificationToken = 'verificationtoken123',
    @StatusUser = 1,

    @IdRole = 1,
    @PersonType = 'C'; -- 'C' para Cliente, 'A' para Agente Inmobiliario
