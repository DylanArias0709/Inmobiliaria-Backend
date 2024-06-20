USE [bdInmobiliaria]
GO
/****** Object:  StoredProcedure [dbo].[spRegisterRealStateAgent]    Script Date: 9/6/2024 16:31:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE [dbo].[spRegisterRealStateAgent]
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

    @MaximumBudget DECIMAL(18, 2),
    @IdRole INT
AS
BEGIN
    SET NOCOUNT ON;

	DECLARE @IdAgent INT;

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

        -- Insertar en la tabla tbRealStateAgent
        INSERT INTO tbRealStateAgent (IdUser, MaximumBudget, Status)
        VALUES (@IdUser, @MaximumBudget, 1);
		SET @IdAgent = SCOPE_IDENTITY();
        -- Si todo va bien, confirmar la transacción
        COMMIT TRANSACTION;

        -- Retornar el Id del usuario registrado
        SELECT @IdAgent AS IdAgent;
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

EXEC [dbo].[spRegisterRealStateAgent]
    @Name = 'Ana',
    @FirstSurname = 'Martínez',
    @SecondSurname = 'García',
    @IdCard = '256124564351',

    @IdProvince = 1,
    @IdCanton = 1,
    @IdDistrict = 1,
    @AditionalInformation = 'Avenida 123, San José',

    @Email = 'ana.martinez@example.com',

    @PhoneNumber = '5233543123032',

    @UserName = 'anamartinezz',
    @Password = 'password123',
    @ActivationToken = 'activationtoken456',
    @VerificationToken = 'verificationtoken456',

    @MaximumBudget = 100000.00,
    @IdRole = 1; -- IdRole correspondiente al rol de Agente Inmobiliario


	INSERT INTO tbProvince(Name, Status) VALUES('Heredia', 1)
	INSERT INTO tbCanton(Name, IdProvince, Status) VALUES('Sarapiquí', 1, 1)
	INSERT INTO tbDistrict(Name, IdCanton, Status) VALUES('Horquetas', 1, 1)
