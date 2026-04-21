USE [master]
GO
/****** Object:  Database [bdInmobiliaria]    Script Date: 9/6/2024 15:33:06 ******/
CREATE DATABASE [bdInmobiliaria]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'bdInmobiliaria', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL16.SQLEXPRESS\MSSQL\DATA\bdInmobiliaria.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'bdInmobiliaria_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL16.SQLEXPRESS\MSSQL\DATA\bdInmobiliaria_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT, LEDGER = OFF
GO
ALTER DATABASE [bdInmobiliaria] SET COMPATIBILITY_LEVEL = 160
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [bdInmobiliaria].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [bdInmobiliaria] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [bdInmobiliaria] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [bdInmobiliaria] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [bdInmobiliaria] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [bdInmobiliaria] SET ARITHABORT OFF 
GO
ALTER DATABASE [bdInmobiliaria] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [bdInmobiliaria] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [bdInmobiliaria] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [bdInmobiliaria] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [bdInmobiliaria] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [bdInmobiliaria] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [bdInmobiliaria] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [bdInmobiliaria] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [bdInmobiliaria] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [bdInmobiliaria] SET  DISABLE_BROKER 
GO
ALTER DATABASE [bdInmobiliaria] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [bdInmobiliaria] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [bdInmobiliaria] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [bdInmobiliaria] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [bdInmobiliaria] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [bdInmobiliaria] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [bdInmobiliaria] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [bdInmobiliaria] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [bdInmobiliaria] SET  MULTI_USER 
GO
ALTER DATABASE [bdInmobiliaria] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [bdInmobiliaria] SET DB_CHAINING OFF 
GO
ALTER DATABASE [bdInmobiliaria] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [bdInmobiliaria] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [bdInmobiliaria] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [bdInmobiliaria] SET ACCELERATED_DATABASE_RECOVERY = OFF  
GO
ALTER DATABASE [bdInmobiliaria] SET QUERY_STORE = ON
GO
ALTER DATABASE [bdInmobiliaria] SET QUERY_STORE (OPERATION_MODE = READ_WRITE, CLEANUP_POLICY = (STALE_QUERY_THRESHOLD_DAYS = 30), DATA_FLUSH_INTERVAL_SECONDS = 900, INTERVAL_LENGTH_MINUTES = 60, MAX_STORAGE_SIZE_MB = 1000, QUERY_CAPTURE_MODE = AUTO, SIZE_BASED_CLEANUP_MODE = AUTO, MAX_PLANS_PER_QUERY = 200, WAIT_STATS_CAPTURE_MODE = ON)
GO
USE [bdInmobiliaria]
GO
/****** Object:  Table [dbo].[tbAgreement]    Script Date: 9/6/2024 15:33:06 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbAgreement](
	[IdAgreement] [int] IDENTITY(1,1) NOT NULL,
	[IdProperty] [int] NULL,
	[IdClient] [int] NULL,
	[IdRealStateAgent] [int] NULL,
	[AgreementDate] [date] NULL,
	[AditionalInformation] [varchar](200) NULL,
	[Status] [tinyint] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[IdAgreement] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbCanton]    Script Date: 9/6/2024 15:33:06 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbCanton](
	[IdCanton] [int] IDENTITY(1,1) NOT NULL,
	[IdProvince] [int] NULL,
	[Name] [varchar](200) NULL,
	[Status] [tinyint] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[IdCanton] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbClient]    Script Date: 9/6/2024 15:33:06 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbClient](
	[IdClient] [int] IDENTITY(1,1) NOT NULL,
	[Budget] [decimal](10, 2) NULL,
	[IdUser] [int] NULL,
	[Status] [tinyint] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[IdClient] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbClientPreference]    Script Date: 9/6/2024 15:33:06 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbClientPreference](
	[IdClientPreference] [int] IDENTITY(1,1) NOT NULL,
	[IdClient] [int] NULL,
	[MinimumPrice] [decimal](10, 2) NULL,
	[MaximumPrice] [decimal](10, 2) NULL,
	[AmountMinimunRooms] [int] NULL,
	[AmountMaximumRooms] [int] NULL,
	[AmountMinimumBathrooms] [int] NULL,
	[AmountMaximumBathrooms] [int] NULL,
	[MinimumArea] [int] NULL,
	[MaximumArea] [int] NULL,
	[MinimumYearConstruction] [int] NULL,
	[MaximumYearConstruction] [int] NULL,
	[AdicionalCharacteristics] [varchar](200) NULL,
	[Status] [tinyint] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[IdClientPreference] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbComment]    Script Date: 9/6/2024 15:33:06 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbComment](
	[IdComment] [int] IDENTITY(1,1) NOT NULL,
	[IdUser] [int] NULL,
	[CommentContent] [varchar](200) NULL,
	[CommentPublicationDate] [date] NULL,
	[Status] [tinyint] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[IdComment] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbComunication]    Script Date: 9/6/2024 15:33:06 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbComunication](
	[IdComunication] [int] IDENTITY(1,1) NOT NULL,
	[IdComunicationType] [int] NULL,
	[IdClient] [int] NOT NULL,
	[IdRealStateAgent] [int] NOT NULL,
	[DateTimeComunication] [datetime] NULL,
	[Status] [tinyint] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[IdComunication] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbComunicationType]    Script Date: 9/6/2024 15:33:06 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbComunicationType](
	[IdComunicationType] [int] IDENTITY(1,1) NOT NULL,
	[NameComunicationType] [varchar](100) NULL,
	[Status] [tinyint] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[IdComunicationType] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbDirection]    Script Date: 9/6/2024 15:33:06 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbDirection](
	[IdDirection] [int] IDENTITY(1,1) NOT NULL,
	[IdProvince] [int] NULL,
	[IdCanton] [int] NULL,
	[IdDistrict] [int] NULL,
	[AditionalInformation] [varchar](200) NULL,
	[Status] [tinyint] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[IdDirection] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbDistrict]    Script Date: 9/6/2024 15:33:06 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbDistrict](
	[IdDistrict] [int] IDENTITY(1,1) NOT NULL,
	[IdCanton] [int] NULL,
	[Name] [varchar](200) NULL,
	[Status] [tinyint] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[IdDistrict] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbEmail]    Script Date: 9/6/2024 15:33:06 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbEmail](
	[IdEmail] [int] IDENTITY(1,1) NOT NULL,
	[IdPerson] [int] NULL,
	[Email] [varchar](100) NULL,
	[Status] [tinyint] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[IdEmail] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbImage]    Script Date: 9/6/2024 15:33:06 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbImage](
	[IdImage] [int] IDENTITY(1,1) NOT NULL,
	[Image] [varbinary](max) NULL,
	[RegistrationDate] [date] NULL,
	[Status] [tinyint] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[IdImage] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbPaymentMethod]    Script Date: 9/6/2024 15:33:06 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbPaymentMethod](
	[IdPaymentMethod] [int] IDENTITY(1,1) NOT NULL,
	[TypePaymentMethod] [varchar](100) NULL,
	[Status] [tinyint] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[IdPaymentMethod] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbPerson]    Script Date: 9/6/2024 15:33:06 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbPerson](
	[IdPerson] [int] IDENTITY(1,1) NOT NULL,
	[Name] [varchar](100) NULL,
	[FirstSurname] [varchar](100) NULL,
	[SecondSurname] [varchar](100) NULL,
	[IdCard] [varchar](100) NULL,
	[IdDirection] [int] NULL,
	[Status] [tinyint] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[IdPerson] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbPhone]    Script Date: 9/6/2024 15:33:06 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbPhone](
	[IdPhone] [int] IDENTITY(1,1) NOT NULL,
	[IdPerson] [int] NULL,
	[PhoneNumber] [varchar](20) NULL,
	[Status] [tinyint] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[IdPhone] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbProperty]    Script Date: 9/6/2024 15:33:06 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbProperty](
	[IdProperty] [int] IDENTITY(1,1) NOT NULL,
	[IdRealStateAgent] [int] NULL,
	[IdPropertyDetail] [int] NULL,
	[Price] [decimal](10, 2) NULL,
	[IdClient] [int] NULL,
	[IdImage] [int] NULL,
	[Status] [tinyint] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[IdProperty] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbPropertyDetail]    Script Date: 9/6/2024 15:33:06 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbPropertyDetail](
	[IdPropertyDetails] [int] IDENTITY(1,1) NOT NULL,
	[IdPropertyType] [int] NULL,
	[NumberOfFloors] [int] NULL,
	[TerrainArea] [decimal](10, 2) NULL,
	[BuiltArea] [decimal](10, 2) NULL,
	[AmountRooms] [int] NULL,
	[AmountBathrooms] [int] NULL,
	[YearBuilt] [date] NULL,
	[AditionalInformation] [varchar](200) NULL,
	[IdServiceType] [int] NULL,
	[IdProperty] [int] NULL,
	[Status] [tinyint] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[IdPropertyDetails] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbPropertyType]    Script Date: 9/6/2024 15:33:06 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbPropertyType](
	[IdPropertyType] [int] IDENTITY(1,1) NOT NULL,
	[PropertyTypeName] [varchar](100) NULL,
	[PropertyTypeDescription] [varchar](100) NULL,
	[Status] [tinyint] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[IdPropertyType] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbProvince]    Script Date: 9/6/2024 15:33:06 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbProvince](
	[IdProvince] [int] IDENTITY(1,1) NOT NULL,
	[Name] [varchar](100) NULL,
	[Status] [tinyint] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[IdProvince] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbPublicationProperty]    Script Date: 9/6/2024 15:33:06 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbPublicationProperty](
	[IdPublicationProperty] [int] IDENTITY(1,1) NOT NULL,
	[IdProperty] [int] NULL,
	[PublicationDate] [date] NULL,
	[ExpirationDate] [date] NULL,
	[IdRealStateAgent] [int] NULL,
	[Status] [tinyint] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[IdPublicationProperty] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbRealStateAgent]    Script Date: 9/6/2024 15:33:06 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbRealStateAgent](
	[IdRealStateAgent] [int] IDENTITY(1,1) NOT NULL,
	[IdUser] [int] NULL,
	[IdClientPreference] [int] NULL,
	[MaximumBudget] [decimal](10, 2) NULL,
	[Status] [tinyint] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[IdRealStateAgent] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbRent]    Script Date: 9/6/2024 15:33:06 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbRent](
	[IdRent] [int] IDENTITY(1,1) NOT NULL,
	[IdAgreement] [int] NULL,
	[RentPrice] [decimal](10, 2) NULL,
	[StartDateRent] [date] NULL,
	[EndDateRent] [date] NULL,
	[MonthDuration] [int] NULL,
	[InitialDeposit] [decimal](10, 2) NULL,
	[Status] [tinyint] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[IdRent] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbRole]    Script Date: 9/6/2024 15:33:06 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbRole](
	[IdRole] [int] IDENTITY(1,1) NOT NULL,
	[RoleName] [varchar](100) NULL,
	[Status] [tinyint] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[IdRole] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbSale]    Script Date: 9/6/2024 15:33:06 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbSale](
	[IdSale] [int] IDENTITY(1,1) NOT NULL,
	[IdAgreement] [int] NULL,
	[IdClient] [int] NULL,
	[IdRealStateAgent] [int] NULL,
	[SaleDate] [date] NULL,
	[AditionalInformation] [varchar](200) NULL,
	[IdPaymentMethod] [int] NULL,
	[Status] [tinyint] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[IdSale] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbServiceType]    Script Date: 9/6/2024 15:33:06 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbServiceType](
	[IdServiceType] [int] IDENTITY(1,1) NOT NULL,
	[NameServiceType] [varchar](100) NULL,
	[Status] [tinyint] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[IdServiceType] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbSesion]    Script Date: 9/6/2024 15:33:06 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbSesion](
	[IdSesion] [int] IDENTITY(1,1) NOT NULL,
	[TokenSesion] [varchar](500) NULL,
	[RegistrationSesionDate] [date] NULL,
	[ActualizationSesionDate] [date] NULL,
	[ExpirationSesionDate] [date] NULL,
	[IdUser] [int] NULL,
	[Status] [tinyint] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[IdSesion] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbUser]    Script Date: 9/6/2024 15:33:06 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbUser](
	[IdUser] [int] IDENTITY(1,1) NOT NULL,
	[UserName] [varchar](100) NULL,
	[RegistrationDate] [date] NULL,
	[Password] [varchar](100) NULL,
	[ActivationToken] [varchar](500) NULL,
	[VerificationToken] [varchar](500) NULL,
	[IdPerson] [int] NULL,
	[IdRole] [int] NULL,
	[Status] [tinyint] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[IdUser] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbVisit]    Script Date: 9/6/2024 15:33:06 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbVisit](
	[IdVisit] [int] IDENTITY(1,1) NOT NULL,
	[IdProperty] [int] NULL,
	[IdRealStateAgent] [int] NULL,
	[IdClient] [int] NULL,
	[IdComment] [int] NULL,
	[IdCalification] [int] NULL,
	[VisitDate] [date] NULL,
	[Status] [tinyint] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[IdVisit] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[tbAgreement]  WITH CHECK ADD FOREIGN KEY([IdClient])
REFERENCES [dbo].[tbClient] ([IdClient])
GO
ALTER TABLE [dbo].[tbAgreement]  WITH CHECK ADD FOREIGN KEY([IdProperty])
REFERENCES [dbo].[tbProperty] ([IdProperty])
GO
ALTER TABLE [dbo].[tbAgreement]  WITH CHECK ADD FOREIGN KEY([IdRealStateAgent])
REFERENCES [dbo].[tbRealStateAgent] ([IdRealStateAgent])
GO
ALTER TABLE [dbo].[tbCanton]  WITH CHECK ADD FOREIGN KEY([IdProvince])
REFERENCES [dbo].[tbProvince] ([IdProvince])
GO
ALTER TABLE [dbo].[tbClient]  WITH CHECK ADD FOREIGN KEY([IdUser])
REFERENCES [dbo].[tbUser] ([IdUser])
GO
ALTER TABLE [dbo].[tbClientPreference]  WITH CHECK ADD FOREIGN KEY([IdClient])
REFERENCES [dbo].[tbClient] ([IdClient])
GO
ALTER TABLE [dbo].[tbComment]  WITH CHECK ADD FOREIGN KEY([IdUser])
REFERENCES [dbo].[tbUser] ([IdUser])
GO
ALTER TABLE [dbo].[tbComunication]  WITH CHECK ADD FOREIGN KEY([IdClient])
REFERENCES [dbo].[tbClient] ([IdClient])
GO
ALTER TABLE [dbo].[tbComunication]  WITH CHECK ADD FOREIGN KEY([IdComunicationType])
REFERENCES [dbo].[tbComunicationType] ([IdComunicationType])
GO
ALTER TABLE [dbo].[tbComunication]  WITH CHECK ADD FOREIGN KEY([IdRealStateAgent])
REFERENCES [dbo].[tbRealStateAgent] ([IdRealStateAgent])
GO
ALTER TABLE [dbo].[tbDirection]  WITH CHECK ADD FOREIGN KEY([IdCanton])
REFERENCES [dbo].[tbCanton] ([IdCanton])
GO
ALTER TABLE [dbo].[tbDirection]  WITH CHECK ADD FOREIGN KEY([IdDistrict])
REFERENCES [dbo].[tbDistrict] ([IdDistrict])
GO
ALTER TABLE [dbo].[tbDirection]  WITH CHECK ADD FOREIGN KEY([IdProvince])
REFERENCES [dbo].[tbProvince] ([IdProvince])
GO
ALTER TABLE [dbo].[tbDistrict]  WITH CHECK ADD FOREIGN KEY([IdCanton])
REFERENCES [dbo].[tbCanton] ([IdCanton])
GO
ALTER TABLE [dbo].[tbEmail]  WITH CHECK ADD FOREIGN KEY([IdPerson])
REFERENCES [dbo].[tbPerson] ([IdPerson])
GO
ALTER TABLE [dbo].[tbPerson]  WITH CHECK ADD FOREIGN KEY([IdDirection])
REFERENCES [dbo].[tbDirection] ([IdDirection])
GO
ALTER TABLE [dbo].[tbPhone]  WITH CHECK ADD FOREIGN KEY([IdPerson])
REFERENCES [dbo].[tbPerson] ([IdPerson])
GO
ALTER TABLE [dbo].[tbProperty]  WITH CHECK ADD FOREIGN KEY([IdClient])
REFERENCES [dbo].[tbClient] ([IdClient])
GO
ALTER TABLE [dbo].[tbProperty]  WITH CHECK ADD FOREIGN KEY([IdImage])
REFERENCES [dbo].[tbImage] ([IdImage])
GO
ALTER TABLE [dbo].[tbPropertyDetail]  WITH CHECK ADD FOREIGN KEY([IdPropertyType])
REFERENCES [dbo].[tbPropertyType] ([IdPropertyType])
GO
ALTER TABLE [dbo].[tbPropertyDetail]  WITH CHECK ADD FOREIGN KEY([IdProperty])
REFERENCES [dbo].[tbProperty] ([IdProperty])
GO
ALTER TABLE [dbo].[tbPropertyDetail]  WITH CHECK ADD FOREIGN KEY([IdServiceType])
REFERENCES [dbo].[tbServiceType] ([IdServiceType])
GO
ALTER TABLE [dbo].[tbPublicationProperty]  WITH CHECK ADD FOREIGN KEY([IdProperty])
REFERENCES [dbo].[tbProperty] ([IdProperty])
GO
ALTER TABLE [dbo].[tbPublicationProperty]  WITH CHECK ADD FOREIGN KEY([IdRealStateAgent])
REFERENCES [dbo].[tbRealStateAgent] ([IdRealStateAgent])
GO
ALTER TABLE [dbo].[tbRealStateAgent]  WITH CHECK ADD FOREIGN KEY([IdUser])
REFERENCES [dbo].[tbUser] ([IdUser])
GO
ALTER TABLE [dbo].[tbRent]  WITH CHECK ADD FOREIGN KEY([IdAgreement])
REFERENCES [dbo].[tbAgreement] ([IdAgreement])
GO
ALTER TABLE [dbo].[tbSale]  WITH CHECK ADD FOREIGN KEY([IdAgreement])
REFERENCES [dbo].[tbAgreement] ([IdAgreement])
GO
ALTER TABLE [dbo].[tbSale]  WITH CHECK ADD FOREIGN KEY([IdClient])
REFERENCES [dbo].[tbClient] ([IdClient])
GO
ALTER TABLE [dbo].[tbSale]  WITH CHECK ADD FOREIGN KEY([IdPaymentMethod])
REFERENCES [dbo].[tbPaymentMethod] ([IdPaymentMethod])
GO
ALTER TABLE [dbo].[tbSale]  WITH CHECK ADD FOREIGN KEY([IdRealStateAgent])
REFERENCES [dbo].[tbRealStateAgent] ([IdRealStateAgent])
GO
ALTER TABLE [dbo].[tbSesion]  WITH CHECK ADD FOREIGN KEY([IdUser])
REFERENCES [dbo].[tbUser] ([IdUser])
GO
ALTER TABLE [dbo].[tbUser]  WITH CHECK ADD FOREIGN KEY([IdPerson])
REFERENCES [dbo].[tbPerson] ([IdPerson])
GO
ALTER TABLE [dbo].[tbUser]  WITH CHECK ADD FOREIGN KEY([IdRole])
REFERENCES [dbo].[tbRole] ([IdRole])
GO
ALTER TABLE [dbo].[tbVisit]  WITH CHECK ADD FOREIGN KEY([IdClient])
REFERENCES [dbo].[tbClient] ([IdClient])
GO
ALTER TABLE [dbo].[tbVisit]  WITH CHECK ADD FOREIGN KEY([IdComment])
REFERENCES [dbo].[tbComment] ([IdComment])
GO
ALTER TABLE [dbo].[tbVisit]  WITH CHECK ADD FOREIGN KEY([IdProperty])
REFERENCES [dbo].[tbProperty] ([IdProperty])
GO
ALTER TABLE [dbo].[tbVisit]  WITH CHECK ADD FOREIGN KEY([IdRealStateAgent])
REFERENCES [dbo].[tbRealStateAgent] ([IdRealStateAgent])
GO
/****** Object:  StoredProcedure [dbo].[spRegisterClient]    Script Date: 9/6/2024 15:33:06 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[spRegisterClient]
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
GO
/****** Object:  StoredProcedure [dbo].[spRegisterPerson]    Script Date: 9/6/2024 15:33:06 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[spRegisterPerson]
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
GO
/****** Object:  StoredProcedure [dbo].[spRegisterRealStateAgent]    Script Date: 9/6/2024 15:33:06 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[spRegisterRealStateAgent]
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

        -- Verificar si el PhoneNumber ya está asociado a una persona
        IF EXISTS (SELECT 1 FROM tbPhone WHERE PhoneNumber = @PhoneNumber)
        BEGIN
            RAISERROR ('El Numero de telefono ya está asociado a una persona.', 16, 1);
            RETURN -6;
        END

        -- Verificar si el ActivationToken ya existe en tbUser
        IF EXISTS (SELECT 1 FROM tbUser WHERE ActivationToken = @ActivationToken)
        BEGIN
            RAISERROR ('El token de activación ya existe.', 16, 1);
            RETURN -7;
        END

        -- Verificar si el VerificationToken ya existe en tbUser
        IF EXISTS (SELECT 1 FROM tbUser WHERE VerificationToken = @VerificationToken)
        BEGIN
            RAISERROR ('El token de verificación ya existe.', 16, 1);
            RETURN -8;
        END

        -- Verificar si el IdRole existe en tbRole
        IF NOT EXISTS (SELECT 1 FROM tbRole WHERE IdRole = @IdRole)
        BEGIN
            RAISERROR ('El ID de rol no existe.', 16, 1);
            RETURN -9;
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

        -- Insertar en la tabla tbRealStateAgent
        INSERT INTO tbRealStateAgent (IdUser, MaximumBudget, Status)
        VALUES (@IdUser, @MaximumBudget, @StatusRealStateAgent);

        -- Si todo va bien, confirmar la transacción
        COMMIT TRANSACTION;

        -- Retornar el Id del usuario registrado
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
GO
/****** Object:  StoredProcedure [dbo].[spUpdateClient]    Script Date: 9/6/2024 15:33:06 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[spUpdateClient]
    @ClientId INT,
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

        -- Obtener @IdUser y @IdPerson a partir de @ClientId
        SELECT 
            @IdUser = u.IdUser,
            @IdPerson = p.IdPerson,
            @IdDirection = d.IdDirection
        FROM tbClient c
        JOIN tbUser u ON c.IdUser = u.IdUser
        JOIN tbPerson p ON u.IdPerson = p.IdPerson
        JOIN tbDirection d ON p.IdDirection = d.IdDirection
        WHERE c.IdClient = @ClientId;

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

        -- Actualizar la tabla tbClient
        UPDATE tbClient
        SET 
            Budget = @Budget,
            Status = @StatusClient
        WHERE IdClient = @ClientId;

        -- Si todo va bien, confirmar la transacción
        COMMIT TRANSACTION;

        -- Retornar el Id del cliente actualizado
        SELECT @ClientId AS IdClient;
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
GO
USE [master]
GO
ALTER DATABASE [bdInmobiliaria] SET  READ_WRITE 
GO
