#!/usr/bin/perl

my %almacen;

my $entrada = "sal.txt";

my $turno;
my $p1="false";
my $p2="false";
my $p3="false";
my $p4="false";

my $p1_t="-1" , $p2_t="-1" , $p3_t="-1" , $p4_t="-1";
my $p1_p="-1" , $p2_p="-1" , $p3_p="-1" , $p4_p="-1";

my $conta = "4";

while ($linea=<>){
		chop($linea);
	if ($linea=~/Turn ([^ \n]*)/){
		$turno = $1;
		
	}elsif($linea=~/Player 1: P1 - ([^ \.]*)/){
		if($turno eq "1"){
			$p1 = $1;
		}elsif($p1 ne $1){
			#print "P1\n";
			#print $turno;
			#print "\n";
			$p1 = $1;
			$p1_t = $turno;
			$p1_p = $conta;
			$conta--;
			}
	}elsif($linea=~/Player 1: P2 - ([^ \.]*)/){
		if($turno eq "1"){
			$p2 = $1;
		}elsif($p2 ne $1){
			#print "P2\n";
			#print $turno;
			#print "\n";
			$p2 = $1;
			$p2_t = $turno;
			$p2_p = $conta;
			$conta--;
			}
	}elsif($linea=~/Player 1: P3 - ([^ \.]*)/){
		if($turno eq "1"){
			$p3 = $1;
		}elsif($p3 ne $1){
			#print "P3\n";
			#print $turno;
			#print "\n";
			$p3 = $1;
			$p3_t = $turno;
			$p3_p = $conta;
			$conta--;
			}
	}elsif($linea=~/Player 1: P4 - ([^ \.]*)/){
		if($turno eq "1"){
			$p4 = $1;
		}elsif($p4 ne $1){
			#print "P4\n";
			#print $turno;
			#print "\n";
			$p4 = $1;
			$p4_t = $turno;
			$p4_p = $conta;
			$conta--;
			}
	}elsif($linea=~/Player ([^ \n]*) Wins!/){
		#print $linea;
		#print "Gana\nP";
		#print $1;
		#print "\n"
	}
}

if($p1_t eq "-1"){
	$p1_p = $conta;
}
if($p2_t eq "-1"){
	$p2_p = $conta;
}
if($p3_t eq "-1"){
	$p3_p = $conta;
}
if($p4_t eq "-1"){
	$p4_p = $conta;
}
if($p1_t eq "-1"){
	$p1_t = $turno;
}
if($p2_t eq "-1"){
	$p2_t = $turno;
}
if($p3_t eq "-1"){
	$p3_t = $turno;
}
if($p4_t eq "-1"){
	$p4_t = $turno;
}

print $p1_p;
print "\n";
print $p1_t;
print "\n";
print $p2_p;
print "\n";
print $p2_t;
print "\n";
print $p3_p;
print "\n";
print $p3_t;
print "\n";
print $p4_p;
print "\n";
print $p4_t;
print "\n";

#print $p1_t . "\n" . $p2_t . "\n" . $p3_t . "\n" . $p4_t . "\n";
#print $p1_p . "\n" . $p2_p . "\n" . $p3_p . "\n" . $p4_p . "\n";
