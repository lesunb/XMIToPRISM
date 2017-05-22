dtmc

module 
	sLifeline0 : [-1..1] init 0;
	sLifeline1 : [-1..1] init 0;

	[fail_Lifeline0] sLifeline0=-1 -> 1.0:(sLifeline0'=-1);
	[init_Lifeline0] sLifeline0=0 -> 0.99:(sLifeline0'=1) + 0.01:(sLifeline0'=-1);
	[end_Lifeline0] sLifeline0=1 -> 1.0:(sLifeline0'=1);
	[init_Lifeline1] sLifeline1=0 & sLifeline0=1 -> 1.0:(sLifeline1'=1);
	[fail_Lifeline1] sLifeline1=-1 -> 1.0:(sLifeline1'=-1);
	[end_Lifeline1] sLifeline1=1 -> 1.0:(sLifeline1'=1);
endmodule
