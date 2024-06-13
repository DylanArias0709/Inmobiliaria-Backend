ALTER PROCEDURE spCreateComunication
    @IdComunicationType INT,
    @IdClient INT,
    @IdRealStateAgent INT,

    @NameComunicationType VARCHAR(100), -- A�ad� el tama�o m�ximo del campo

    @IdComunication INT OUT
AS
BEGIN
    BEGIN TRAN
        BEGIN TRY
            -- Verificar si el IdClient existe en tbClient
            IF NOT EXISTS (SELECT 1 FROM tbClient WHERE IdClient = @IdClient)
            BEGIN
                RAISERROR ('El ID del cliente no existe.', 16, 1);
                RETURN -1;
            END

            -- Verificar si el IdRealStateAgent existe en tbRealStateAgent
            IF NOT EXISTS (SELECT 1 FROM tbRealStateAgent WHERE IdRealStateAgent = @IdRealStateAgent)
            BEGIN
                RAISERROR ('El ID del agente no existe.', 16, 1);
                RETURN -2;
            END

            -- Verificar si el IdComunicationType existe en tbComunicationType
            IF NOT EXISTS (SELECT 1 FROM tbComunicationType WHERE IdComunicationType = @IdComunicationType)
            BEGIN
                IF @NameComunicationType IS NOT NULL
                BEGIN
                    INSERT INTO tbComunicationType (NameComunicationType, Status)
                    VALUES (@NameComunicationType, 1);
                    
                    -- Obtener el Id del nuevo tipo de comunicaci�n insertado
                    SET @IdComunicationType = SCOPE_IDENTITY();
                END
                ELSE
                BEGIN
                    RAISERROR ('El ID del tipo de comunicaci�n no existe y los par�metros NameComunicationType y ComunicationTypeStatus est�n vac�os.', 16, 1);
                    RETURN -3;
                END
            END

            INSERT INTO tbComunication (IdComunicationType, IdClient, IdRealStateAgent, DateTimeComunication, Status)
            VALUES (@IdComunicationType, @IdClient, @IdRealStateAgent, GETUTCDATE(), 1);

            SET @IdComunication = SCOPE_IDENTITY();

            SELECT @IdComunication AS IdComunication;

            COMMIT;
        END TRY
        BEGIN CATCH
            ROLLBACK;

            -- Manejar el error
            DECLARE @ErrorMessage NVARCHAR(4000) = ERROR_MESSAGE();
            DECLARE @ErrorSeverity INT = ERROR_SEVERITY();
            DECLARE @ErrorState INT = ERROR_STATE();
            RAISERROR(@ErrorMessage, @ErrorSeverity, @ErrorState);
        END CATCH
END;
GO


DECLARE @IdComunication INT;

EXEC spCreateComunication
    @IdComunicationType = 1, -- ID del tipo de comunicaci�n existente
    @IdClient = 1, -- ID del cliente existente
    @IdRealStateAgent = 1, -- ID del agente de bienes ra�ces existente
    @NameComunicationType = 'Celular',

    @IdComunication = @IdComunication OUTPUT;

SELECT * FROM tbComunication

-- Inserts para la tabla tbProvince (7 provincias de Costa Rica)
INSERT INTO tbProvince (Name, Status) VALUES ('San Jos�', 1);
INSERT INTO tbProvince (Name, Status) VALUES ('Alajuela', 1);
INSERT INTO tbProvince (Name, Status) VALUES ('Cartago', 1);
INSERT INTO tbProvince (Name, Status) VALUES ('Heredia', 1);
INSERT INTO tbProvince (Name, Status) VALUES ('Guanacaste', 1);
INSERT INTO tbProvince (Name, Status) VALUES ('Puntarenas', 1);
INSERT INTO tbProvince (Name, Status) VALUES ('Lim�n', 1);

-- Inserts para la tabla tbCanton (cantones asociados a las provincias)
-- Provincia de San Jos�
INSERT INTO tbCanton (IdProvince, Name, Status) VALUES (1, 'San Jos�', 1);
INSERT INTO tbCanton (IdProvince, Name, Status) VALUES (1, 'Escaz�', 1);
INSERT INTO tbCanton (IdProvince, Name, Status) VALUES (1, 'Desamparados', 1);
INSERT INTO tbCanton (IdProvince, Name, Status) VALUES (1, 'Puriscal', 1);
INSERT INTO tbCanton (IdProvince, Name, Status) VALUES (1, 'Tarraz�', 1);
-- Otros cantones de San Jos�...

-- Provincia de Alajuela
INSERT INTO tbCanton (IdProvince, Name, Status) VALUES (2, 'Alajuela', 1);
INSERT INTO tbCanton (IdProvince, Name, Status) VALUES (2, 'San Ram�n', 1);
INSERT INTO tbCanton (IdProvince, Name, Status) VALUES (2, 'Grecia', 1);
INSERT INTO tbCanton (IdProvince, Name, Status) VALUES (2, 'Atenas', 1);
-- Otros cantones de Alajuela...

-- Provincia de Cartago
INSERT INTO tbCanton (IdProvince, Name, Status) VALUES (3, 'Cartago', 1);
INSERT INTO tbCanton (IdProvince, Name, Status) VALUES (3, 'Para�so', 1);
INSERT INTO tbCanton (IdProvince, Name, Status) VALUES (3, 'La Uni�n', 1);
INSERT INTO tbCanton (IdProvince, Name, Status) VALUES (3, 'Turrialba', 1);
-- Otros cantones de Cartago...

-- Provincia de Heredia
INSERT INTO tbCanton (IdProvince, Name, Status) VALUES (4, 'Heredia', 1);
INSERT INTO tbCanton (IdProvince, Name, Status) VALUES (4, 'Barva', 1);
INSERT INTO tbCanton (IdProvince, Name, Status) VALUES (4, 'Santo Domingo', 1);
INSERT INTO tbCanton (IdProvince, Name, Status) VALUES (4, 'Santa B�rbara', 1);
-- Otros cantones de Heredia...

-- Provincia de Guanacaste
INSERT INTO tbCanton (IdProvince, Name, Status) VALUES (5, 'Liberia', 1);
INSERT INTO tbCanton (IdProvince, Name, Status) VALUES (5, 'Nicoya', 1);
INSERT INTO tbCanton (IdProvince, Name, Status) VALUES (5, 'Santa Cruz', 1);
INSERT INTO tbCanton (IdProvince, Name, Status) VALUES (5, 'Bagaces', 1);
-- Otros cantones de Guanacaste...

-- Provincia de Puntarenas
INSERT INTO tbCanton (IdProvince, Name, Status) VALUES (6, 'Puntarenas', 1);
INSERT INTO tbCanton (IdProvince, Name, Status) VALUES (6, 'Esparza', 1);
INSERT INTO tbCanton (IdProvince, Name, Status) VALUES (6, 'Buenos Aires', 1);
INSERT INTO tbCanton (IdProvince, Name, Status) VALUES (6, 'Montes de Oro', 1);
-- Otros cantones de Puntarenas...

-- Provincia de Lim�n
INSERT INTO tbCanton (IdProvince, Name, Status) VALUES (7, 'Lim�n', 1);
INSERT INTO tbCanton (IdProvince, Name, Status) VALUES (7, 'Pococ�', 1);
INSERT INTO tbCanton (IdProvince, Name, Status) VALUES (7, 'Siquirres', 1);
INSERT INTO tbCanton (IdProvince, Name, Status) VALUES (7, 'Talamanca', 1);
-- Otros cantones de Lim�n...

-- Inserts para la tabla tbDistrict (distritos asociados a los cantones de Costa Rica)

-- Distritos de San Jos�
INSERT INTO tbDistrict (IdCanton, Name, Status) VALUES (1, 'Carmen', 1);
INSERT INTO tbDistrict (IdCanton, Name, Status) VALUES (1, 'Merced', 1);
INSERT INTO tbDistrict (IdCanton, Name, Status) VALUES (1, 'Hospital', 1);
INSERT INTO tbDistrict (IdCanton, Name, Status) VALUES (1, 'Catedral', 1);
-- Otros distritos de San Jos�...

-- Distritos de Escaz�
INSERT INTO tbDistrict (IdCanton, Name, Status) VALUES (2, 'Escaz�', 1);
INSERT INTO tbDistrict (IdCanton, Name, Status) VALUES (2, 'San Antonio', 1);
INSERT INTO tbDistrict (IdCanton, Name, Status) VALUES (2, 'San Rafael', 1);
INSERT INTO tbDistrict (IdCanton, Name, Status) VALUES (2, 'San Juan', 1);
-- Otros distritos de Escaz�...

-- Distritos de Desamparados
INSERT INTO tbDistrict (IdCanton, Name, Status) VALUES (3, 'Desamparados', 1);
INSERT INTO tbDistrict (IdCanton, Name, Status) VALUES (3, 'San Miguel', 1);
INSERT INTO tbDistrict (IdCanton, Name, Status) VALUES (3, 'San Juan de Dios', 1);
INSERT INTO tbDistrict (IdCanton, Name, Status) VALUES (3, 'San Rafael Arriba', 1);
-- Otros distritos de Desamparados...

-- Distritos de Puriscal
INSERT INTO tbDistrict (IdCanton, Name, Status) VALUES (4, 'Puriscal', 1);
INSERT INTO tbDistrict (IdCanton, Name, Status) VALUES (4, 'Santiago', 1);
INSERT INTO tbDistrict (IdCanton, Name, Status) VALUES (4, 'Mercedes Sur', 1);
INSERT INTO tbDistrict (IdCanton, Name, Status) VALUES (4, 'Barbacoas', 1);
-- Otros distritos de Puriscal...

-- Distritos de Tarraz�
INSERT INTO tbDistrict (IdCanton, Name, Status) VALUES (5, 'San Marcos', 1);
INSERT INTO tbDistrict (IdCanton, Name, Status) VALUES (5, 'San Lorenzo', 1);
INSERT INTO tbDistrict (IdCanton, Name, Status) VALUES (5, 'San Carlos', 1);
INSERT INTO tbDistrict (IdCanton, Name, Status) VALUES (5, 'San Juan de Chicu�', 1);
-- Otros distritos de Tarraz�...

-- Puedes continuar con los dem�s distritos asociados a los cantones de las otras provincias de Costa Rica.

