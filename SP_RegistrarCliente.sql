CREATE PROCEDURE spRegisterClient
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

    @Budget DECIMAL(18, 2),
    @StatusClient TINYINT,
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

		-- Verificar si el Email ya está asociado a una persona
        IF EXISTS (SELECT 1 FROM tbPhone WHERE PhoneNumber = @PhoneNumber)
        BEGIN
            RAISERROR ('El Numero de telefono ya está asociado a una persona.', 16, 1);
            RETURN -5;
        END

        -- Verificar si el ActivationToken ya existe en tbUser
        IF EXISTS (SELECT 1 FROM tbUser WHERE ActivationToken = @ActivationToken)
        BEGIN
            RAISERROR ('El token de activación ya existe.', 16, 1);
            RETURN -6;
        END

        -- Verificar si el VerificationToken ya existe en tbUser
        IF EXISTS (SELECT 1 FROM tbUser WHERE VerificationToken = @VerificationToken)
        BEGIN
            RAISERROR ('El token de verificación ya existe.', 16, 1);
            RETURN -7;
        END

        -- Verificar si el IdRole existe en tbRole
        IF NOT EXISTS (SELECT 1 FROM tbRole WHERE IdRole = @IdRole)
        BEGIN
            RAISERROR ('El ID de rol no existe.', 16, 1);
            RETURN -8;
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

        -- Insertar en la tabla tbClient
        INSERT INTO tbClient (Budget, IdUser, Status)
        VALUES (@Budget, @IdUser, @StatusClient);

        -- Si todo va bien, confirmar la transacción
        COMMIT TRANSACTION;

        -- Retornar el Id del cliente registrado
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

DECLARE @IdRole INT = 1; -- Supongamos que el ID del rol es 1
DECLARE @IdProvince INT = 1; -- Supongamos que el ID de la provincia es 1
DECLARE @IdCanton INT = 1; -- Supongamos que el ID del cantón es 1
DECLARE @IdDistrict INT = 1; -- Supongamos que el ID del distrito es 1

EXEC spRegisterClient
    @Name = 'Joan',
    @FirstSurname = 'Perez',
    @SecondSurname = 'Ugalde',
    @IdCard = '568974156',
    @IdProvince = @IdProvince,
    @IdCanton = @IdCanton,
    @IdDistrict = @IdDistrict,
    @AditionalInformation = 'Información adicional',
    @StatusPerson = 1,
    @Email = 'joan.perez2@example.com',
    @StatusEmail = 1,
    @PhoneNumber = '1234567890',
    @StatusPhone = 1,
    @UserName = 'juanperez',
    @Password = 'mypassword',
    @ActivationToken = 'myactivationToken2',
    @VerificationToken = 'myverificationToken2',
    @StatusUser = 1,
    @Budget = 1000.00,
    @StatusClient = 1,
    @StatusDirection = 1,
    @IdRole = @IdRole

Select * FROM tbPerson
SELECT * FROM tbUser
SELECT * FROM tbClient


INSERT INTO tbProvince (Name, Status)
VALUES 
('San José', 1),
('Alajuela', 1),
('Cartago', 1),
('Heredia', 1),
('Guanacaste', 1),
('Puntarenas', 1),
('Limón', 1);


SELECT * FROM tbProvince

INSERT INTO tbCanton (IdProvince, Name, Status)
VALUES 
(1, 'San José', 1),
(1, 'Escazú', 1),
(1, 'Desamparados', 1),
(1, 'Puriscal', 1),
(1, 'Tarrazú', 1),
(2, 'Alajuela', 1),
(2, 'San Ramón', 1),
(2, 'Grecia', 1),
(2, 'San Mateo', 1),
(3, 'Cartago', 1),
(3, 'Paraíso', 1),
(3, 'La Unión', 1),
(4, 'Heredia', 1),
(4, 'Barva', 1),
(4, 'Santo Domingo', 1),
(5, 'Liberia', 1),
(5, 'Nicoya', 1),
(5, 'Santa Cruz', 1),
(6, 'Puntarenas', 1),
(6, 'Esparza', 1),
(6, 'Buenos Aires', 1),
(7, 'Limón', 1),
(7, 'Pococí', 1),
(7, 'Siquirres', 1);

SELECT * FROM tbCanton

INSERT INTO tbDistrict (IdCanton, Name, Status)
VALUES 
(1, 'Carmen', 1),
(1, 'Merced', 1),
(1, 'Hospital', 1),
(1, 'Catedral', 1),
(1, 'Zapote', 1),
(2, 'San Rafael', 1),
(2, 'San Miguel', 1),
(2, 'San Antonio', 1),
(3, 'San Rafael Arriba', 1),
(3, 'San Rafael Abajo', 1),
(3, 'San Juan de Dios', 1),
(4, 'Santiago', 1),
(4, 'Mercedes Sur', 1),
(4, 'Barbacoas', 1),
(5, 'San Marcos', 1),
(5, 'San Lorenzo', 1),
(5, 'San Carlos', 1),
(6, 'San Isidro', 1),
(6, 'San Rafael', 1),
(6, 'San Ramón', 1),
(7, 'Guadalupe', 1),
(7, 'San Rafael', 1),
(7, 'San Pablo', 1),
(8, 'Liberia', 1),
(8, 'Cañas Dulces', 1),
(8, 'Nacascolo', 1),
(9, 'Nicoya', 1),
(9, 'Mansión', 1),
(9, 'San Antonio', 1),
(10, 'Santa Cruz', 1),
(10, 'Bolsón', 1),
(10, 'Veintisiete de Abril', 1),
(11, 'Puntarenas', 1),
(11, 'Barranca', 1),
(11, 'Chacarita', 1),
(12, 'Espíritu Santo', 1),
(12, 'San Juan Grande', 1),
(12, 'Macacona', 1),
(13, 'Buenos Aires', 1),
(13, 'Volcán', 1),
(13, 'Potrero Grande', 1),
(14, 'Limón', 1),
(14, 'Valle La Estrella', 1),
(14, 'Río Blanco', 1),
(15, 'Guápiles', 1),
(15, 'Jiménez', 1),
(15, 'Cariari', 1),
(16, 'Siquirres', 1),
(16, 'Pacuarito', 1),
(16, 'Río Jiménez', 1);

SELECT * FROM tbDistrict

INSERT INTO tbDirection (IdProvince, IdCanton, IdDistrict, AditionalInformation, Status)
VALUES
(1, 1, 1, 'Cerca del parque central', 1),
(1, 1, 2, 'A la par de la iglesia', 1),
(2, 6, 6, 'Frente a la escuela', 1),
(3, 10, 28, 'Detrás de la municipalidad', 1),
(4, 13, 37, 'Cerca del centro comercial', 1),
(5, 19, 45, 'A un costado del hospital', 1),
(6, 23, 32, 'Al lado del mercado', 1),
(7, 24, 2, 'Frente a la playa', 1);

INSERT INTO tbRole (RoleName, Status) VALUES ('administrador', 1);
SELECT * FROM tbRole

ALTER TABLE tbPerson
ALTER COLUMN IdCard VARCHAR(50);  -- O el tamaño que consideres apropiado
