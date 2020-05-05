# $1 timestamp
# $2 who
# $3 action
# $4 param
# $5 outcome
# $6 description

BEGIN {

	n_accept = 0;
	n_reject = 0;
	
	loginCount = 0;
	totalLogin = 0;
	
	recoveryAttempt = 0;
	
	for(i = 1; i <= NF; i++) {
		if ($3 == enter && $5 == "ACCEPTED")
			roomCount[$4] = 0;
		if($5 == "REJECTED")
			failReason[$6] = 0;
	}			

}


/login/ {

	totalLogin ++;
	
	if ($5 == "ACCEPTED")
		loginCount ++;
		
}

/enter/ {
	
	if ($5 == "ACCEPTED")
		roomCount[$4] ++;
	
}

/ACCEPTED/	{

	n_accept++;
	
}

/REJECTED/	{

	n_reject++;
	failReason[$6] ++;
}

/recovery/ {

	recoveryAttempt ++;
	
}

END {
	
	printf("Log Statistic\n\n");
	
	printf("Login\n");
	printf("Attempted %d successfull login over %d in total.\n", loginCount, totalLogin);
	printf("Attempted recovery: %d\n", recoveryAttempt);
	printf("\n");
	
	printf("Room\n");
	
	maxVisited = 0;
	
	for (key in roomCount)
		if(roomCount[key] > maxVisited){
			maxVisited = roomCount[key];
			maxKey = key;
			
		}
	
	
	printf("The most visited room is %s\n", maxKey);
	
	printf("\n");		
	
	accept_rate = n_accept / (n_accept + n_reject);
	reject_rate = 1 - accept_rate;
	
	printf("Accepted %d operation(s) over %d\n",n_accept, n_accept + n_reject);
	printf("\tAccept rate: %.1f%%\n", accept_rate * 100);
	
	printf("Rejected %d operation(s) over %d\n",n_reject, n_accept + n_reject);
	printf("\tReject rate: %.1f%%\n", reject_rate * 100);
	
	printf("All failed attempts\n")
	
	for(reason in failReason)
		printf("\t%s, %d time(s)\n", reason, failReason[reason]);

}