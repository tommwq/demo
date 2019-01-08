Stop-Process -Name basic_paxos.exe -Force -PassThru

Start-Process .\basic_paxos.exe -ArgumentList "-id","1" 
Start-Process .\basic_paxos.exe -ArgumentList "-id","2" 
Start-Process .\basic_paxos.exe -ArgumentList "-id","3" 
Start-Process .\basic_paxos.exe -ArgumentList "-id","4"
Start-Process .\basic_paxos.exe -ArgumentList "-id","5"
Start-Process .\basic_paxos.exe -ArgumentList "-id","6"
Start-Process .\basic_paxos.exe -ArgumentList "-id","7"
Start-Process .\basic_paxos.exe -ArgumentList "-id","8"
