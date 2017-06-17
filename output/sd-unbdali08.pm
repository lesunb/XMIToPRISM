dtmc

module 
	sLifeline0 : [-1..20] init 0;
	sLifeline1 : [-1..20] init 0;

	[] sLifeline0=6 -> 0.8:(sLifeline0'=7) + 0.2:(sLifeline0'=-1);
	[] sLifeline0=4 -> 0.8:(sLifeline0'=5) + 0.2:(sLifeline0'=-1);
	[] sLifeline0=11 & sLifeline1=12 -> 1.0:(sLifeline0'=12);
	[] sLifeline0=10 -> 0.8:(sLifeline0'=11) + 0.2:(sLifeline0'=-1);
	[] sLifeline0=18 -> 0.8:(sLifeline0'=19) + 0.2:(sLifeline0'=-1);
	[] sLifeline0=14 -> 0.8:(sLifeline0'=15) + 0.2:(sLifeline0'=-1);
	[] sLifeline0=7 & sLifeline1=8 -> 1.0:(sLifeline0'=8);
	[] sLifeline0=13 & sLifeline1=14 -> 1.0:(sLifeline0'=14);
	[] sLifeline0=3 & sLifeline1=4 -> 1.0:(sLifeline0'=4);
	[end_Lifeline0] sLifeline0=20 -> 1.0:(sLifeline0'=20);
	[] sLifeline0=15 & sLifeline1=16 -> 1.0:(sLifeline0'=16);
	[] sLifeline0=17 & sLifeline1=18 -> 1.0:(sLifeline0'=18);
	[] sLifeline0=8 -> 0.8:(sLifeline0'=9) + 0.2:(sLifeline0'=-1);
	[init_Lifeline0] sLifeline0=0 -> 0.8:(sLifeline0'=1) + 0.2:(sLifeline0'=-1);
	[] sLifeline0=12 -> 0.8:(sLifeline0'=13) + 0.2:(sLifeline0'=-1);
	[fail_Lifeline0] sLifeline0=-1 -> 1.0:(sLifeline0'=-1);
	[] sLifeline0=19 & sLifeline1=20 -> 1.0:(sLifeline0'=20);
	[] sLifeline0=5 & sLifeline1=6 -> 1.0:(sLifeline0'=6);
	[] sLifeline0=9 & sLifeline1=10 -> 1.0:(sLifeline0'=10);
	[] sLifeline0=16 -> 0.8:(sLifeline0'=17) + 0.2:(sLifeline0'=-1);
	[] sLifeline0=2 -> 0.8:(sLifeline0'=3) + 0.2:(sLifeline0'=-1);
	[] sLifeline0=1 & sLifeline1=2 -> 1.0:(sLifeline0'=2);
	[] sLifeline1=19 -> 0.9:(sLifeline1'=20) + 0.1:(sLifeline1'=-1);
	[] sLifeline1=5 -> 0.9:(sLifeline1'=6) + 0.1:(sLifeline1'=-1);
	[init_Lifeline1] sLifeline1=0 & sLifeline0=1 -> 1.0:(sLifeline1'=1);
	[] sLifeline1=1 -> 0.9:(sLifeline1'=2) + 0.1:(sLifeline1'=-1);
	[] sLifeline1=13 -> 0.9:(sLifeline1'=14) + 0.1:(sLifeline1'=-1);
	[] sLifeline1=16 & sLifeline0=17 -> 1.0:(sLifeline1'=17);
	[] sLifeline1=18 & sLifeline0=19 -> 1.0:(sLifeline1'=19);
	[] sLifeline1=9 -> 0.9:(sLifeline1'=10) + 0.1:(sLifeline1'=-1);
	[] sLifeline1=6 & sLifeline0=7 -> 1.0:(sLifeline1'=7);
	[end_Lifeline1] sLifeline1=20 -> 1.0:(sLifeline1'=20);
	[] sLifeline1=2 & sLifeline0=3 -> 1.0:(sLifeline1'=3);
	[] sLifeline1=10 & sLifeline0=11 -> 1.0:(sLifeline1'=11);
	[] sLifeline1=14 & sLifeline0=15 -> 1.0:(sLifeline1'=15);
	[] sLifeline1=7 -> 0.9:(sLifeline1'=8) + 0.1:(sLifeline1'=-1);
	[fail_Lifeline1] sLifeline1=-1 -> 1.0:(sLifeline1'=-1);
	[] sLifeline1=15 -> 0.9:(sLifeline1'=16) + 0.1:(sLifeline1'=-1);
	[] sLifeline1=3 -> 0.9:(sLifeline1'=4) + 0.1:(sLifeline1'=-1);
	[] sLifeline1=8 & sLifeline0=9 -> 1.0:(sLifeline1'=9);
	[] sLifeline1=4 & sLifeline0=5 -> 1.0:(sLifeline1'=5);
	[] sLifeline1=17 -> 0.9:(sLifeline1'=18) + 0.1:(sLifeline1'=-1);
	[] sLifeline1=11 -> 0.9:(sLifeline1'=12) + 0.1:(sLifeline1'=-1);
	[] sLifeline1=12 & sLifeline0=13 -> 1.0:(sLifeline1'=13);
endmodule
