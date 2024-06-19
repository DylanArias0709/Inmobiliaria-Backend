USE bdInmobiliaria;
GO

CREATE PROCEDURE spCreatePropertyType
    @PropertyTypeName VARCHAR(100),
    @PropertyTypeDescription VARCHAR(100),
    @NewPropertyTypeID INT OUTPUT
AS
BEGIN
    SET NOCOUNT ON;

    BEGIN TRY
        BEGIN TRANSACTION;

        -- Insertar en la tabla TipoPropiedad
        INSERT INTO tbPropertyType (PropertyTypeName, PropertyTypeDescription, Status)
        VALUES (@PropertyTypeName, @PropertyTypeDescription, 1);

        -- Obtener el ID del nuevo tipo de propiedad
        SET @NewPropertyTypeID = SCOPE_IDENTITY();

        COMMIT TRANSACTION;
    END TRY
    BEGIN CATCH
        ROLLBACK TRANSACTION;

        DECLARE @ErrorMessage NVARCHAR(4000) = ERROR_MESSAGE();
        DECLARE @ErrorSeverity INT = ERROR_SEVERITY();
        DECLARE @ErrorState INT = ERROR_STATE();
        RAISERROR(@ErrorMessage, @ErrorSeverity, @ErrorState);

        -- Envía el mensaje de error al backend
        SELECT @ErrorMessage AS ErrorMessage;
    END CATCH
END;
GO