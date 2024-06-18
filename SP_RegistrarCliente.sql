USE [bdInmobiliaria]
GO
/****** Object:  StoredProcedure [dbo].[spRegisterClient]    Script Date: 9/6/2024 16:26:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE [dbo].[spRegisterClient]
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
    @MaximumBudget DECIMAL(18, 2) = NULL,
    @IdRole INT
AS
BEGIN
    SET NOCOUNT ON;
	DECLARE @IdClient INT;

    BEGIN TRY
        BEGIN TRANSACTION;

		DECLARE @IdUser INT;

		EXEC [dbo].[spRegisterUser]
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
			@IdRole = 1,
			@IdUser = @IdUser OUT;

        -- Insertar en la tabla tbClient
        INSERT INTO tbClient (Budget, IdUser, Status)
        VALUES (@Budget, @IdUser, 1);
		SET @IdClient = SCOPE_IDENTITY();

        -- Si todo va bien, confirmar la transacción
        COMMIT TRANSACTION;

        -- Retornar el Id del cliente registrado
        SELECT @IdClient AS IdClient;
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

EXEC [dbo].[spRegisterClient]
    @Name = 'Dylan',
    @FirstSurname = 'Arias',
    @SecondSurname = 'Duran',
    @IdCard = '12356987',

    @IdProvince = 1,
    @IdCanton = 1,
    @IdDistrict = 1,
    @AditionalInformation = 'Calle 123',

    @Email = 'Dylan.arias@example.com',

    @PhoneNumber = '123456789258',

    @UserName = 'DylanArias',
    @Password = 'securepassword',
    @ActivationToken = 'activationtoken125',
    @VerificationToken = 'verificationtoken125',

    @Budget = 100000.00,
    @IdRole = 2;


	SELECT * FROM tbUser
	SELECT * FROM tbPerson
	DELETE FROM tbUser WHERE IdUser = 1003