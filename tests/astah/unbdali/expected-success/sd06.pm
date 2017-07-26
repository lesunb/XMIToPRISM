dtmc

module 
	sA : [-1..1] init 0;
	sB : [-1..0] init 0;

	[fail_A] sA=-1 -> 1.0:(sA'=-1);
	[end_A] sA=1 -> 1.0:(sA'=1);
	[init_A] sA=0 -> 0.9:(sA'=1) + 0.1:(sA'=-1);
	[fail_B] sB=-1 -> 1.0:(sB'=-1);
	[init_B] sB=0 -> 1.0:(sB'=0);
endmodule
