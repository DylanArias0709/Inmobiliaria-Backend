SELECT * FROM tbRealStateAgent
SELECT * FROM tbClient
SELECT * FROM tbProperty
SELECT * FROM tbImage
SELECT * FROM tbPropertyDetail
SELECT * FROM tbPropertyType
SELECT * FROM tbServiceType

-- Insertar datos en tbProvince
INSERT INTO tbProvince (Name, Status)
VALUES ('San José', 1);

-- Insertar datos en tbCanton
INSERT INTO tbCanton (IdProvince, Name, Status)
VALUES (1, 'San José', 1);

-- Insertar datos en tbDistrict
INSERT INTO tbDistrict (IdCanton, Name, Status)
VALUES (1, 'Pavas', 1);

-- Insertar datos en tbDirection
INSERT INTO tbDirection (IdProvince, IdCanton, IdDistrict, AditionalInformation, Status)
VALUES (1, 1, 1, 'Avenida 2, Calle 4', 1);

-- Insertar datos en tbEmail
INSERT INTO tbEmail (Email, Status)
VALUES ('cliente@example.com', 1);

-- Insertar datos en tbPhone
INSERT INTO tbPhone (PhoneNumber, Status)
VALUES ('12345678', 1);

-- Insertar datos en tbPerson (Cliente)
INSERT INTO tbPerson (Name, FirstSurname, SecondSurname, IdCard, IdEmail, IdPhone, IdDirection, Status)
VALUES ('Juan', 'Pérez', 'Gómez', 123456789, 1, 1, 1, 1);

-- Insertar datos en tbUser (Cliente)
INSERT INTO tbUser (UserName, RegistrationDate, Password, IdPerson, IdRole, Status)
VALUES ('juanperez', GETDATE(), 'contraseña123', SCOPE_IDENTITY(), 1, 1);

-- Insertar datos en tbComunicationType
INSERT INTO tbComunicationType (NameComunicationType, Status)
VALUES ('Correo electrónico', 1);

-- Insertar datos en tbComunication (Cliente)
INSERT INTO tbComunication (IdComunicationType, DateTimeComunication, Status)
VALUES (1, GETDATE(), 1);

-- Insertar datos en tbClient
INSERT INTO tbClient (Budget, IdUser, IdComunication, Status)
VALUES (100000, SCOPE_IDENTITY(), SCOPE_IDENTITY(), 1);

-- Insertar datos en tbClientPreference
INSERT INTO tbClientPreference (IdClient, MinimumPrice, MaximumPrice, AmountMinimunRooms, AmountMaximumRooms, AmountMinimumBathrooms, AmountMaximumBathrooms, MinimumArea, MaximumArea, MinimumYearConstruction, MaximumYearConstruction, AdicionalCharacteristics, Status)
VALUES (SCOPE_IDENTITY(), 50000, 150000, 2, 4, 1, 3, 50, 150, 2000, 2022, 'Cerca de transporte público', 1);

-- Insertar datos en tbRole
INSERT INTO tbRole (RoleName, Status)
VALUES ('Cliente', 1);

-- Insertar datos en tbPerson (Agente Inmobiliario)
INSERT INTO tbPerson (Name, FirstSurname, SecondSurname, IdCard, IdEmail, IdPhone, IdDirection, Status)
VALUES ('Pedro', 'González', 'Martínez', 987654321, 2, 2, 1, 1);

-- Insertar datos en tbUser (Agente Inmobiliario)
INSERT INTO tbUser (UserName, RegistrationDate, Password, IdPerson, IdRole, Status)
VALUES ('pedrogonzalez', GETDATE(), 'contraseña456', SCOPE_IDENTITY(), 2, 1);

-- Insertar datos en tbRealStateAgent
INSERT INTO tbRealStateAgent (IdUser, IdClientPreference, MaximumBudget, IdComunication, Status)
VALUES (SCOPE_IDENTITY(), SCOPE_IDENTITY(), 200000, SCOPE_IDENTITY(), 1);


-- PRUEBA DE EXECT DE NUEVA PROPIEDAD

USE bdInmobiliaria;
GO

DECLARE @IdAgent INT = 1; -- ID de ejemplo para el agente
DECLARE @Price DECIMAL(10, 2) = 250000.00; -- Precio de ejemplo
DECLARE @IdClient INT = 1; -- ID de ejemplo para el cliente
DECLARE @Image VARBINARY(MAX); -- Imagen de ejemplo en formato VARBINARY
DECLARE @IdPropertyType INT = 4; -- ID de ejemplo para el tipo de propiedad
DECLARE @NumberOfFloors INT = 2; -- Número de pisos de ejemplo
DECLARE @TerrainArea DECIMAL(10, 2) = 500.00; -- Área del terreno de ejemplo
DECLARE @BuiltArea DECIMAL(10, 2) = 300.00; -- Área construida de ejemplo
DECLARE @AmountRooms INT = 4; -- Número de habitaciones de ejemplo
DECLARE @AmountBathrooms INT = 3; -- Número de baños de ejemplo
DECLARE @YearBuilt DATE = '2024-06-15'; -- Año de construcción de ejemplo
DECLARE @AdditionalInformation VARCHAR(200) = 'Additional information'; -- Información adicional de ejemplo
DECLARE @IdServiceType INT = 1; -- ID de ejemplo para el tipo de servicio
DECLARE @PropertyTypeName VARCHAR(100) = 'Apartment'; -- Nombre de ejemplo para el tipo de propiedad
DECLARE @PropertyTypeDescription VARCHAR(100) = 'A brand new apartment'; -- Descripción de ejemplo para el tipo de propiedad
DECLARE @NameServiceType VARCHAR(100) = 'For Rent'; -- Nombre de ejemplo para el tipo de servicio

-- Simulando una imagen en formato VARBINARY
SET @Image = 0xFFD8FFE000104A46494600010101006000600000FFDB00430008060607060508;

EXEC spRegisterNewProperty
    @IdAgent,
    @Price,
    @IdClient,
    @Image,
    @IdPropertyType,
    @NumberOfFloors,
    @TerrainArea,
    @BuiltArea,
    @AmountRooms,
    @AmountBathrooms,
    @YearBuilt,
    @AdditionalInformation,
    @IdServiceType,
    @PropertyTypeName,
    @PropertyTypeDescription,
    @NameServiceType;
GO


SELECT * FROM tbRealStateAgent
SELECT * FROM tbClient
SELECT * FROM tbProperty
SELECT * FROM tbImage
SELECT * FROM tbPropertyDetail
SELECT * FROM tbPropertyType
SELECT * FROM tbServiceType


SELECT 
    p.IdProperty,
    p.Price,
    p.IdClient,
    p.IdRealStateAgent,
	p.Status AS PropertyStatus,
    pt.PropertyTypeName,
    pt.PropertyTypeDescription,
    pd.NumberOfFloors,
    pd.TerrainArea,
    pd.BuiltArea,
    pd.AmountRooms,
    pd.AmountBathrooms,
    pd.YearBuilt,
    pd.AditionalInformation,
	pd.Status  AS PropertyDetailsStatus,
    st.NameServiceType,
    i.Image,
	i.Status AS PropertyImageStatus,
    i.RegistrationDate
FROM 
    tbProperty p
INNER JOIN 
    tbPropertyDetail pd ON p.IdProperty = pd.IdProperty
INNER JOIN 
    tbPropertyType pt ON pd.IdPropertyType = pt.IdPropertyType
INNER JOIN 
    tbServiceType st ON pd.IdServiceType = st.IdServiceType
INNER JOIN 
    tbImage i ON p.IdImage = i.IdImage