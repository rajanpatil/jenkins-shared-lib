/*
    Publish HTML reports.
    Parameters:
        reportName - Name under which reports will be published
        reportDir - Report directory where all the report files are generated
        reportFile - Main or index report file name
    Example:
        publishHTMLReport('Test Report', 'test_reports', 'index.html')
 */
def call(String reportName, String reportDir, String reportFile = 'index.html' ){
    publishHTML (target: [
            allowMissing: false,
            alwaysLinkToLastBuild: false,
            keepAll: true,
            reportDir: reportDir,
            reportFiles: reportFile,
            reportName: reportName
    ])
}