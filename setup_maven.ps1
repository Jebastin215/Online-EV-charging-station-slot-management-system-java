$ErrorActionPreference = "Stop"

Write-Host "Checking Java..."
java -version
if ($?) {
    Write-Host "Java is present."
} else {
    Write-Error "Java is not found in PATH. Please install JDK 17+."
    exit 1
}

$mavenVersion = "3.9.6"
$mavenUrl = "https://repo.maven.apache.org/maven2/org/apache/maven/apache-maven/$mavenVersion/apache-maven-$mavenVersion-bin.zip"
$toolsDir = Join-Path $PSScriptRoot "tools"
$mavenInstallDir = Join-Path $toolsDir "apache-maven-$mavenVersion"
$mavenBin = Join-Path $mavenInstallDir "bin"

if (-not (Test-Path "$mavenBin\mvn.cmd")) {
    Write-Host "Maven not found locally. Downloading Maven $mavenVersion..."
    New-Item -ItemType Directory -Force -Path $toolsDir | Out-Null
    $zipPath = Join-Path $toolsDir "maven.zip"
    
    # Download
    Invoke-WebRequest -Uri $mavenUrl -OutFile $zipPath -UseBasicParsing
    
    Write-Host "Extracting Maven..."
    Expand-Archive -Path $zipPath -DestinationPath $toolsDir -Force
    
    # Cleanup
    Remove-Item $zipPath -ErrorAction SilentlyContinue
}

# Add Maven to current session PATH
$env:PATH = "$mavenBin;$env:PATH"

Write-Host "Maven setup complete. Version:"
mvn -version

Write-Host "`nBuilding Project..."
mvn clean package

if ($?) {
    Write-Host "`nStarting Application..."
    mvn cargo:run
} else {
    Write-Error "Build failed."
}
