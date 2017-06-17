dtmc

module 
	sLifeline0 : [-1..1] init 0;
	sLifeline1 : [-1..0] init 0;

	[end_Lifeline0] sLifeline0=1 -> 1.0:(sLifeline0'=1);
	[fail_Lifeline0] sLifeline0=-1 -> 1.0:(sLifeline0'=-1);
	[init_Lifeline0] sLifeline0=0 -> 0.9:(sLifeline0'=1) + 0.1:(sLifeline0'=-1);
	[fail_Lifeline1] sLifeline1=-1 -> 1.0:(sLifeline1'=-1);
	[init_Lifeline1] sLifeline1=0 -> 1.0:(sLifeline1'=0);
endmodule
