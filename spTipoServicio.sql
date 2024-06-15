USE bdInmobiliaria;
GO

CREATE PROCEDURE spCreateServiceType
    @NameServiceType VARCHAR(100),
    @NewServiceTypeID INT OUTPUT
AS
BEGIN
    SET NOCOUNT ON;

    BEGIN TRY
        BEGIN TRANSACTION;

        -- Insertar en la tabla TipoServicio
        INSERT INTO tbServiceType (NameServiceType, Status)
        VALUES (@NameServiceType, 1);

        -- Obtener el ID del nuevo tipo de servicio
        SET @NewServiceTypeID = SCOPE_IDENTITY();

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