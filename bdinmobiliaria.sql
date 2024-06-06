USE bdInmobiliaria;

-- Entidad Provincia
CREATE TABLE tbProvince (
    IdProvince INT PRIMARY KEY IDENTITY (1,1),
    Name VARCHAR (100),
    Status TINYINT NOT NULL
);

-- Entidad Canton
CREATE TABLE tbCanton (
    IdCanton INT PRIMARY KEY IDENTITY (1,1),
    IdProvince INT,
    Name VARCHAR (200),
    Status TINYINT NOT NULL,
    FOREIGN KEY (IdProvince) REFERENCES tbProvince (IdProvince)
);

-- Entidad Distrito
CREATE TABLE tbDistrict (
    IdDistrict INT PRIMARY KEY IDENTITY (1,1),
    IdCanton INT,
    Name VARCHAR (200),
    Status TINYINT NOT NULL,
    FOREIGN KEY (IdCanton) REFERENCES tbCanton (IdCanton)
);

-- Entidad Direccion
CREATE TABLE tbDirection (
    IdDirection INT PRIMARY KEY IDENTITY (1,1),
    IdProvince INT, 
    IdCanton INT,
    IdDistrict INT,
    AditionalInformation VARCHAR(200),
    Status TINYINT NOT NULL,
    FOREIGN KEY (IdProvince) REFERENCES tbProvince (IdProvince),
    FOREIGN KEY (IdCanton) REFERENCES tbCanton (IdCanton),
    FOREIGN KEY (IdDistrict) REFERENCES tbDistrict (IdDistrict)
);

-- Entidad Persona
CREATE TABLE tbPerson (
    IdPerson INT PRIMARY KEY IDENTITY(1,1),
    Name VARCHAR(100),
    FirstSurname VARCHAR(100),
    SecondSurname VARCHAR(100),
    IdCard VARCHAR(100),
    IdDirection INT,
    Status TINYINT NOT NULL,
    FOREIGN KEY (IdDirection) REFERENCES tbDirection(IdDirection)
);

-- Entidad Email
CREATE TABLE tbEmail (
    IdEmail INT PRIMARY KEY IDENTITY (1,1),
    IdPerson INT,
    Email VARCHAR(100),
    Status TINYINT NOT NULL,
    FOREIGN KEY (IdPerson) REFERENCES tbPerson(IdPerson)
);

-- Entidad Telefono
CREATE TABLE tbPhone (
    IdPhone INT PRIMARY KEY IDENTITY (1,1),
    IdPerson INT,
    PhoneNumber VARCHAR(20),
    Status TINYINT NOT NULL,
    FOREIGN KEY (IdPerson) REFERENCES tbPerson(IdPerson)
);

-- Entidad Rol
CREATE TABLE tbRole (
    IdRole INT PRIMARY KEY IDENTITY(1,1),
    RoleName VARCHAR(100),
    Status TINYINT NOT NULL
);

-- Entidad Usuario
CREATE TABLE tbUser (
    IdUser INT PRIMARY KEY IDENTITY (1,1),
    UserName VARCHAR (100),
    RegistrationDate DATE,
    Password VARCHAR (100),
    ActivationToken VARCHAR(500),
    VerificationToken VARCHAR(500),
    IdPerson INT,
    IdRole INT,
    FOREIGN KEY (IdPerson) REFERENCES tbPerson(IdPerson),
    FOREIGN KEY (IdRole) REFERENCES tbRole(IdRole),
    Status TINYINT NOT NULL
);

-- Entidad Sesion
CREATE TABLE tbSesion (
    IdSesion INT PRIMARY KEY IDENTITY (1,1),
    TokenSesion VARCHAR (500),
    RegistrationSesionDate DATE,
    ActualizationSesionDate DATE,
    ExpirationSesionDate DATE,
    IdUser INT,
    Status TINYINT NOT NULL,
    FOREIGN KEY (IdUser) REFERENCES tbUser (IdUser)
);

-- Entidad Tipo De Comunicacion
CREATE TABLE tbComunicationType (
    IdComunicationType INT PRIMARY KEY IDENTITY (1,1),
    NameComunicationType VARCHAR(100),
    Status TINYINT NOT NULL
);

-- Entidad Comunicacion
CREATE TABLE tbComunication (
    IdComunication INT PRIMARY KEY IDENTITY (1,1),
    IdComunicationType INT,
    DateTimeComunication DATETIME,
    Status TINYINT NOT NULL,
    FOREIGN KEY (IdComunicationType) REFERENCES tbComunicationType (IdComunicationType)
);

-- Entidad Cliente
CREATE TABLE tbClient (
    IdClient INT PRIMARY KEY IDENTITY (1,1),
    Budget DECIMAL (10,2),
    IdUser INT,
    IdComunication INT,
    Status TINYINT NOT NULL,
    FOREIGN KEY (IdUser) REFERENCES tbUser (IdUser),
    FOREIGN KEY (IdComunication) REFERENCES tbComunication (IdComunication)
);

-- Entidad Cliente Preferencia
CREATE TABLE tbClientPreference (
    IdClientPreference INT PRIMARY KEY IDENTITY (1,1),
    IdClient INT,
    MinimumPrice DECIMAL (10,2),
    MaximumPrice DECIMAL (10,2),
    AmountMinimunRooms INT,
    AmountMaximumRooms INT,
    AmountMinimumBathrooms INT,
    AmountMaximumBathrooms INT,
    MinimumArea INT,
    MaximumArea INT,
    MinimumYearConstruction INT, 
    MaximumYearConstruction INT,
    AdicionalCharacteristics VARCHAR(200),
    Status TINYINT NOT NULL,
    FOREIGN KEY (IdClient) REFERENCES tbClient(IdClient)
);

-- Entidad Agente Inmobiliario
CREATE TABLE tbRealStateAgent (
    IdRealStateAgent INT PRIMARY KEY IDENTITY (1,1),
    IdUser INT,
    IdClientPreference INT,
    MaximumBudget DECIMAL(10,2),
    IdComunication INT,
    Status TINYINT NOT NULL,
    FOREIGN KEY (IdUser) REFERENCES tbUser(IdUser),
    FOREIGN KEY (IdComunication) REFERENCES tbComunication(IdComunication)
);

-- Entidad Tipo De Servicio
CREATE TABLE tbServiceType (
    IdServiceType INT PRIMARY KEY IDENTITY (1,1),
    NameServiceType VARCHAR (100),
    Status TINYINT NOT NULL
);

-- Entidad Tipo De Propiedad
CREATE TABLE tbPropertyType (
    IdPropertyType INT PRIMARY KEY IDENTITY (1,1),
    PropertyTypeName VARCHAR(100),
    PropertyTypeDescription VARCHAR (100),
    Status TINYINT NOT NULL
);

-- Entidad Imagen
CREATE TABLE tbImage (
    IdImage INT PRIMARY KEY IDENTITY (1,1),
    Image VARBINARY(MAX),
    RegistrationDate DATE,
    Status TINYINT NOT NULL
);

-- Entidad Propiedad
CREATE TABLE tbProperty (
    IdProperty INT PRIMARY KEY IDENTITY (1,1),
    IdRealStateAgent INT,
    IdPropertyDetail INT,
    Price DECIMAL (10,2),
    IdClient INT,
    IdImage INT,
    Status TINYINT NOT NULL,
    FOREIGN KEY (IdClient) REFERENCES tbClient (IdClient),
    FOREIGN KEY (IdImage) REFERENCES tbImage (IdImage)
);

-- Entidad Propiedad Detalle
CREATE TABLE tbPropertyDetail (
    IdPropertyDetails INT PRIMARY KEY IDENTITY (1,1),
    IdPropertyType INT,
    NumberOfFloors INT,
    TerrainArea DECIMAL(10,2),
    BuiltArea DECIMAL(10,2),
    AmountRooms INT,
    AmountBathrooms INT,
    YearBuilt DATE,
    AditionalInformation VARCHAR(200),
    IdServiceType INT,
    IdProperty INT,
    Status TINYINT NOT NULL,
    FOREIGN KEY (IdPropertyType) REFERENCES tbPropertyType(IdPropertyType),
    FOREIGN KEY (IdServiceType) REFERENCES tbServiceType(IdServiceType),
    FOREIGN KEY (IdProperty) REFERENCES tbProperty(IdProperty)
);

-- Entidad Contrato
CREATE TABLE tbAgreement (
    IdAgreement INT PRIMARY KEY IDENTITY (1,1),
    IdProperty INT,
    IdClient INT,
    IdRealStateAgent INT,
    AgreementDate DATE,
    AditionalInformation VARCHAR (200),
    Status TINYINT NOT NULL,
    FOREIGN KEY (IdProperty) REFERENCES tbProperty(IdProperty),
    FOREIGN KEY (IdClient) REFERENCES tbClient(IdClient),
    FOREIGN KEY (IdRealStateAgent) REFERENCES tbRealStateAgent(IdRealStateAgent)
);

-- Entidad Metodo De Pago
CREATE TABLE tbPaymentMethod (
    IdPaymentMethod INT PRIMARY KEY IDENTITY (1,1),
    TypePaymentMethod VARCHAR (100),
    Status TINYINT NOT NULL
);

-- Entidad Venta
CREATE TABLE tbSale (
    IdSale INT PRIMARY KEY IDENTITY (1,1),
    IdAgreement INT,
    IdClient INT,
    IdRealStateAgent INT,
    SaleDate DATE,
    AditionalInformation VARCHAR(200),
    IdPaymentMethod INT,
    Status TINYINT NOT NULL,
    FOREIGN KEY (IdAgreement) REFERENCES tbAgreement(IdAgreement),
    FOREIGN KEY (IdClient) REFERENCES tbClient(IdClient),
    FOREIGN KEY (IdRealStateAgent) REFERENCES tbRealStateAgent(IdRealStateAgent),
    FOREIGN KEY (IdPaymentMethod) REFERENCES tbPaymentMethod(IdPaymentMethod)
);

-- Entidad Alquiler
CREATE TABLE tbRent (
    IdRent INT PRIMARY KEY IDENTITY (1,1),
    IdAgreement INT,
    RentPrice DECIMAL(10,2),
    StartDateRent DATE,
    EndDateRent DATE,
    MonthDuration INT,
    InitialDeposit DECIMAL (10,2),
    Status TINYINT NOT NULL,
    FOREIGN KEY (IdAgreement) REFERENCES tbAgreement (IdAgreement)
);

-- Entidad Comentario
CREATE TABLE tbComment (
    IdComment INT PRIMARY KEY IDENTITY (1,1),
    IdUser INT,
    CommentContent VARCHAR(200),
    CommentPublicationDate DATE,
    Status TINYINT NOT NULL,
    FOREIGN KEY (IdUser) REFERENCES tbUser (IdUser)
);

-- Entidad Visita
CREATE TABLE tbVisit (
    IdVisit INT PRIMARY KEY IDENTITY (1,1),
    IdProperty INT,
    IdRealStateAgent INT,
    IdClient INT,
    IdComment INT,
    IdCalification INT,
    VisitDate DATE,
    Status TINYINT NOT NULL,
    FOREIGN KEY (IdProperty) REFERENCES tbProperty (IdProperty),
    FOREIGN KEY (IdRealStateAgent) REFERENCES tbRealStateAgent (IdRealStateAgent),
    FOREIGN KEY (IdClient) REFERENCES tbClient (IdClient),
    FOREIGN KEY (IdComment) REFERENCES tbComment (IdComment)
);

-- Entidad Publicacion Propiedad
CREATE TABLE tbPublicationProperty (
    IdPublicationProperty INT PRIMARY KEY IDENTITY (1,1),
    IdProperty INT,
    PublicationDate DATE,
    ExpirationDate DATE,
    IdRealStateAgent INT,
    Status TINYINT NOT NULL,
    FOREIGN KEY (IdProperty) REFERENCES tbProperty(IdProperty),
    FOREIGN KEY (IdRealStateAgent) REFERENCES tbRealStateAgent(IdRealStateAgent)
);
