$isAdmin = ([Security.Principal.WindowsPrincipal] [Security.Principal.WindowsIdentity]::GetCurrent()).IsInRole([Security.Principal.WindowsBuiltInRole]::Administrator)

if (-not $isAdmin) {
    Start-Process powershell -Verb RunAs -ArgumentList "-NoExit -ExecutionPolicy Bypass -File `"$PSScriptRoot\run-tests-admin.ps1`""
    exit
}

Set-Location $PSScriptRoot
Write-Host "Running as Administrator..."
.\gradlew.bat test
