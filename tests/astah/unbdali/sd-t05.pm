dtmc

module 
	sA : [-1..1] init 0;

	[end_A] sA=1 -> 1.0:(sA'=1);
	[fail_A] sA=-1 -> 1.0:(sA'=-1);
	[init_A] sA=0 -> 0.9:(sA'=1) + 0.1:(sA'=-1);
endmodule
