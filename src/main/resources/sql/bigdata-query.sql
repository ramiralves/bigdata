<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<sqlMap>
    <sqls>
        <entry>
            <key>SELECT_FILE_LOG</key>
            <value><![CDATA[
				SELECT
					ID_FILE_LOG,
					NAME_FILE_LOG,
					DATE_FILE_LOG,
					SIZE_FILE_LOG,
					(SELECT LEVEL_LOG.TOTAL_LEVEL_LOG FROM MYDB.LEVEL_LOG LEVEL_LOG WHERE LEVEL_LOG.ID_LEVEL_LOG = FILE_LOG.ID_FILE_LOG AND LEVEL_LOG.DESC_LEVEL_LOG = 'DEBUG' ) AS 'TOTAL_DEBUG',
					(SELECT LEVEL_LOG.TOTAL_LEVEL_LOG FROM MYDB.LEVEL_LOG LEVEL_LOG WHERE LEVEL_LOG.ID_LEVEL_LOG = FILE_LOG.ID_FILE_LOG AND LEVEL_LOG.DESC_LEVEL_LOG = 'ERROR' ) AS 'TOTAL_ERROR',
					(SELECT LEVEL_LOG.TOTAL_LEVEL_LOG FROM MYDB.LEVEL_LOG LEVEL_LOG WHERE LEVEL_LOG.ID_LEVEL_LOG = FILE_LOG.ID_FILE_LOG AND LEVEL_LOG.DESC_LEVEL_LOG = 'FATAL' ) AS 'TOTAL_FATAL',
					(SELECT LEVEL_LOG.TOTAL_LEVEL_LOG FROM MYDB.LEVEL_LOG LEVEL_LOG WHERE LEVEL_LOG.ID_LEVEL_LOG = FILE_LOG.ID_FILE_LOG AND LEVEL_LOG.DESC_LEVEL_LOG = 'INFO'  ) AS 'TOTAL_INFO',
					(SELECT LEVEL_LOG.TOTAL_LEVEL_LOG FROM MYDB.LEVEL_LOG LEVEL_LOG WHERE LEVEL_LOG.ID_LEVEL_LOG = FILE_LOG.ID_FILE_LOG AND LEVEL_LOG.DESC_LEVEL_LOG = 'TRACE' ) AS 'TOTAL_TRACE',
					(SELECT LEVEL_LOG.TOTAL_LEVEL_LOG FROM MYDB.LEVEL_LOG LEVEL_LOG WHERE LEVEL_LOG.ID_LEVEL_LOG = FILE_LOG.ID_FILE_LOG AND LEVEL_LOG.DESC_LEVEL_LOG = 'WARN'  ) AS 'TOTAL_WARN'
					
				FROM
					MYDB.FILE_LOG FILE_LOG
            ]]></value>
        </entry>
	</sqls>
</sqlMap>