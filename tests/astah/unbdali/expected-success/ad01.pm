dtmc

module Teste1
	sTeste1 : [-1..2] init 0;

	[] sTeste1=0 -> 1.0:(sTeste1'=1);
	[A0_initial] sTeste1=1 -> 0.9:(sTeste1'=2) + 0.1:(sTeste1'=-1);
	[A0_fail] sTeste1=-1 -> 1.0:(sTeste1'=-1);
	[A0_final] sTeste1=2 -> 1.0:(sTeste1'=2);
endmodule
