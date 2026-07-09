@echo off
title Build and Run Ticket Sales Information System
echo Launching build process via PowerShell...
powershell -NoProfile -ExecutionPolicy Bypass -File "%~dp0run.ps1"
if %ERRORLEVEL% neq 0 (
    echo.
    echo Script encountered an error.
)
pause
