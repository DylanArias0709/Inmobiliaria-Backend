CREATE PROCEDURE spRegisterPerson
    @Name VARCHAR(100),
    @FirstSurname VARCHAR(100),
    @SecondSurname VARCHAR(100),
    @IdCard INT,
    @IdProvince INT,
    @IdCanton INT,
    @IdDistrict INT,
    @AditionalInformation VARCHAR(200),
    @Status TINYINT,
    @Email VARCHAR(100),
    @PhoneNumber VARCHAR(20)
AS
BEGIN
    SET NOCOUNT ON;

    DECLARE @IdPerson INT;
    DECLARE @IdDirection INT;

    BEGIN TRY
        BEGIN TRANSACTION;

        -- Insertar en la tabla tbDirection
        INSERT INTO tbDirection (IdProvince, IdCanton, IdDistrict, AditionalInformation, Status)
        VALUES (@IdProvince, @IdCanton, @IdDistrict, @AditionalInformation, @Status);

        -- Obtener el IdDirection recién insertado
        SET @IdDirection = SCOPE_IDENTITY();

        -- Insertar en la tabla tbPerson
        INSERT INTO tbPerson (Name, FirstSurname, SecondSurname, IdCard, IdDirection, Status)
        VALUES (@Name, @FirstSurname, @SecondSurname, @IdCard, @IdDirection, @Status);

        -- Obtener el IdPerson recién insertado
        SET @IdPerson = SCOPE_IDENTITY();

        -- Insertar en la tabla tbEmail
        INSERT INTO tbEmail (IdPerson, Email, Status)
        VALUES (@IdPerson, @Email, @Status);

        -- Insertar en la tabla tbPhone
        INSERT INTO tbPhone (IdPerson, PhoneNumber, Status)
        VALUES (@IdPerson, @PhoneNumber, @Status);

        -- Si todo va bien, confirmar la transacción
        COMMIT TRANSACTION;

        -- Retornar el Id de la persona registrada
        SELECT @IdPerson AS IdPerson;
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


-- Ejemplo de ejecución del procedimiento almacenado
EXEC spRegisterPerson 
    @Name = 'Juan',
    @FirstSurname = 'Pérez',
    @SecondSurname = 'López',
    @IdCard = 12345678,
    @IdProvince = 1,
    @IdCanton = 1,
    @IdDistrict = 1,
    @AditionalInformation = 'Información adicional',
    @Status = 1,
    @Email = 'juan.perez@example.com',
    @PhoneNumber = '1234567890';



Select * FROM tbPerson


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


ALTER TABLE tbPerson
ALTER COLUMN IdCard VARCHAR(50);  -- O el tamaño que consideres apropiado
