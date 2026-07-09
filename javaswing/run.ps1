# Automated Build & Run script for Ticket Sales Information System

$ErrorActionPreference = "Stop"

# 1. Check and Start MySQL Service
Write-Host "==================================================" -ForegroundColor Cyan
Write-Host "  1. MEMERIKSA LAYANAN DATABASE MYSQL (XAMPP)    " -ForegroundColor Cyan
Write-Host "==================================================" -ForegroundColor Cyan

$mysqlPath = "C:\xampp\mysql\bin\mysqld.exe"
$mysqlDefaultFile = "C:\xampp\mysql\bin\my.ini"

# Test connection to port 3306
$portOpen = $false
try {
    $socket = New-Object System.Net.Sockets.TcpClient
    $connect = $socket.BeginConnect("localhost", 3306, $null, $null)
    $success = $connect.AsyncWaitHandle.WaitOne(1000, $true)
    if ($success) {
        $socket.EndConnect($connect)
        $portOpen = $true
    }
    $socket.Close()
}
catch {
    # Port is closed
}

if ($portOpen) {
    Write-Host "MySQL sudah berjalan aktif di port 3306." -ForegroundColor Green
}
else {
    Write-Host "MySQL tidak terdeteksi aktif. Mencoba menjalankan MySQL Server..." -ForegroundColor Yellow
    if (Test-Path $mysqlPath) {
        Start-Process -FilePath $mysqlPath -ArgumentList "--defaults-file=$mysqlDefaultFile", "--standalone" -WindowStyle Hidden
        Write-Host "Menunggu MySQL Server menyala..." -ForegroundColor Yellow
        Start-Sleep -Seconds 4
        Write-Host "MySQL Server berhasil dijalankan di background." -ForegroundColor Green
    }
    else {
        Write-Host "EROR: Tidak dapat menemukan executable MySQL di '$mysqlPath'. Silakan jalankan MySQL via XAMPP Control Panel secara manual." -ForegroundColor Red
        Pause
        Exit
    }
}

# 2. Seed Database Schema
Write-Host ""
Write-Host "==================================================" -ForegroundColor Cyan
Write-Host "  2. KONFIGURASI DAN SEEDING DATABASE SCHEMA       " -ForegroundColor Cyan
Write-Host "==================================================" -ForegroundColor Cyan

$mysqlClientPath = "C:\xampp\mysql\bin\mysql.exe"
if (Test-Path $mysqlClientPath) {
    Write-Host "Mengimpor skema database dari 'db/db_penjualan_tiket.sql'..." -ForegroundColor Yellow
    & $mysqlClientPath -u root -e "source db/db_penjualan_tiket.sql"
    if ($LASTEXITCODE -eq 0) {
        Write-Host "Database berhasil dikonfigurasi dan diisi data awal." -ForegroundColor Green
    }
    else {
        Write-Host "Peringatan: Gagal melakukan seeding database otomatis. Kode keluar: $LASTEXITCODE" -ForegroundColor Red
    }
}
else {
    Write-Host "Peringatan: mysql.exe client tidak ditemukan. Pastikan database 'db_penjualan_tiket' diimpor secara manual." -ForegroundColor Red
}

# 3. Compile Java Source Files
Write-Host ""
Write-Host "==================================================" -ForegroundColor Cyan
Write-Host "  3. MELAKUKAN KOMPILASI KODE PROGRAM JAVA       " -ForegroundColor Cyan
Write-Host "==================================================" -ForegroundColor Cyan

$javacPath = "C:\Program Files\Eclipse Adoptium\jdk-21.0.10.7-hotspot\bin\javac.exe"
$javaPath = "C:\Program Files\Eclipse Adoptium\jdk-21.0.10.7-hotspot\bin\java.exe"

if (-not (Test-Path $javacPath)) {
    Write-Host "EROR: JDK 21 tidak ditemukan di '$javacPath'." -ForegroundColor Red
    Pause
    Exit
}

# Clean and recreate build output directory 'bin'
if (Test-Path bin) {
    Remove-Item -Recurse -Force bin
}
New-Item -ItemType Directory -Path bin | Out-Null

# Retrieve all java files
$javaFiles = Get-ChildItem -Path src -Filter *.java -Recurse | ForEach-Object { $_.FullName }
if ($javaFiles.Count -eq 0) {
    Write-Host "EROR: Tidak ada file sumber Java ditemukan di folder 'src'." -ForegroundColor Red
    Pause
    Exit
}

Write-Host "Mengompilasi $($javaFiles.Count) file Java..." -ForegroundColor Yellow
& $javacPath -d bin -cp "lib/*;bin" $javaFiles

if ($LASTEXITCODE -ne 0) {
    Write-Host "Kompilasi Gagal! Periksa kembali kode program." -ForegroundColor Red
    Pause
    Exit
}
Write-Host "Kompilasi sukses. File class disimpan di folder 'bin'." -ForegroundColor Green

# 4. Run Application
Write-Host ""
Write-Host "==================================================" -ForegroundColor Cyan
Write-Host "  4. MENJALANKAN APLIKASI PENJUALAN TIKET        " -ForegroundColor Cyan
Write-Host "==================================================" -ForegroundColor Cyan

Write-Host "Memulai aplikasi com.ticketapp.App..." -ForegroundColor Green
Start-Sleep -Seconds 1
& $javaPath -cp "bin;lib/*" com.ticketapp.App
