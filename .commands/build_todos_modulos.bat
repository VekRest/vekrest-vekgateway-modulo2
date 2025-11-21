@echo off
setlocal enabledelayedexpansion

echo ==========================================
echo CONFIGURACOES INICIAIS
echo ==========================================
echo.

set MODULES=vekgateway veksecurity vekclient
echo MÓDULOS: %MODULES%

echo.

echo CRIANDO PASTA DE LOGS

IF NOT EXIST "logs-bat" (
    mkdir "logs-bat"
)

echo.

echo ==========================================
echo      BUILD DE TODOS OS MODULOS
echo ==========================================

for %%M in (%MODULES%) do (

    echo ===== PROCESSANDO MODULO %%M =====

    echo.
    echo ------------------------------------------
    echo PROCESSANDO MODULO: %%M
    echo ------------------------------------------

    cd %%M

    REM ===========================
    REM   MAVEN BUILD
    REM ===========================

    echo Executando Maven no modulo %%M...
    mvn clean package -DskipTests >> ../logs-bat/logs-maven-%%M.txt 2>&1

    if errorlevel 1 (
	echo [ERRO] Maven build falhou no módulo %%M
	timeout /t 30
	cd ..
        goto error_exit
    )

    echo Maven OK para %%M

    REM ===========================
    REM   DOCKER BUILD
    REM ===========================

    echo Excluindo imagem Docker: vekrest/%%M:latest ANTIGA...
    docker rmi vekrest/%%M:latest

    if errorlevel 1 (
        echo [WARN] Não há imagem Docker ou ela está em uso e não pode ser deletada
    )

    echo Construindo imagem Docker: vekrest/%%M:latest NOVA...
    cd %%M
    docker build -t vekrest/%%M:latest . >> ../logs-bat/logs-docker-%%M.txt 2>&1

    if errorlevel 1 (
        echo [ERRO] Docker build falhou no módulo %%M
	timeout /t 30
	cd ..
        goto error_exit
    )

    echo Docker OK para %%M

    cd ..
)

goto success_exit


:error_exit
echo.
echo ==========================================
echo   ERRO DURANTE O BUILD
echo ==========================================
goto wait_and_exit


:success_exit
echo.
echo ==========================================
echo        TODOS OS MODULOS CONSTRUIDOS
echo ==========================================
goto wait_and_exit


:wait_and_exit
echo.
pause
