@echo off
for /f "tokens=1-4 delims=/ " %%i in ("%date%") do (
     set dow=%%i
     set month=%%j
     set day=%%k
     set year=%%l
)
set datr=%month%%day%%year%
set num=%random% %%100
exp payroll/payroll@payroll file=D:\DataBackup\%datr%%num%.dmp & exit