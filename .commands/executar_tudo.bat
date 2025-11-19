@echo off

echo ==========================================
echo CONFIGURACOES INICIAIS
echo ==========================================
echo.

set MODULE=vekclient
echo Modulo definido: %MODULE%
echo.


echo ==========================================
echo         BUILD E DEPLOY DO MODULO
echo ==========================================

echo ===== PROCESSANDO MODULO: %MODULE% =====
echo.

call maven.bat
cd .commands
call docker.bat

echo ==========================================
echo         BUILD E DEPLOY COMPLETOS
echo ==========================================
pause
