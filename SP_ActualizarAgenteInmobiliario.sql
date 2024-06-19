USE [bdInmobiliaria]
GO
/****** Object:  StoredProcedure [dbo].[spUpdateClient]    Script Date: 9/6/2024 16:32:22 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[spUpdateRealStateAgent]
    @RealStateAgentId INT,
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

        -- Obtener @IdUser y @IdPerson a partir de @RealStateAgentId
        SELECT 
            @IdUser = u.IdUser,
            @IdPerson = p.IdPerson,
            @IdDirection = d.IdDirection
        FROM tbRealStateAgent ra
        JOIN tbUser u ON ra.IdUser = u.IdUser
        JOIN tbPerson p ON u.IdPerson = p.IdPerson
        JOIN tbDirection d ON p.IdDirection = d.IdDirection
        WHERE ra.IdRealStateAgent = @RealStateAgentId;

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

         -- Actualizar la tabla tbRealStateAgent
        UPDATE tbRealStateAgent
        SET 
            MaximumBudget = @MaximumBudget,
            Status = @StatusRealStateAgent
        WHERE IdRealStateAgent = @RealStateAgentId;

        -- Si todo va bien, confirmar la transacción
        COMMIT TRANSACTION;

        -- Retornar el Id del cliente actualizado
        SELECT @RealStateAgentId AS IdRealStateAgent;
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