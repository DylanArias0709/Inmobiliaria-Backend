USE bdInmobiliaria;
GO

CREATE PROCEDURE spUpdatePropertyType
    @IdPropertyType INT,
    @NewNamePropertyType VARCHAR(100),
	@NewNamePropertyTypeDescription VARCHAR(100),
	@Status TINYINT
AS
BEGIN
    SET NOCOUNT ON;

    BEGIN TRY
        BEGIN TRANSACTION;

        -- Verificar si el tipo de servicio existe
        IF NOT EXISTS (SELECT 1 FROM tbPropertyType WHERE IdPropertyType = @IdPropertyType)
        BEGIN
            RAISERROR('Property Type does not exist.', 16, 1);
            RETURN;
        END

        -- Actualizar el tipo de propiedad
        UPDATE tbPropertyType
        SET PropertyTypeName = @NewNamePropertyType,
			PropertyTypeDescription = @NewNamePropertyTypeDescription,
			Status = @Status
        WHERE IdPropertyType = @IdPropertyType;

        COMMIT TRANSACTION;
    END TRY
    BEGIN CATCH
        -- Verificar si hay una transacción activa antes de intentar hacer un ROLLBACK
        IF @@TRANCOUNT > 0
            ROLLBACK TRANSACTION;

        -- Capturar y lanzar el error
        DECLARE @ErrorMessage NVARCHAR(4000) = ERROR_MESSAGE();
        DECLARE @ErrorSeverity INT = ERROR_SEVERITY();
        DECLARE @ErrorState INT = ERROR_STATE();
        RAISERROR(@ErrorMessage, @ErrorSeverity, @ErrorState);

        -- Envía el mensaje de error al backend
        SELECT @ErrorMessage AS ErrorMessage;
    END CATCH
END;
GO