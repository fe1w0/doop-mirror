grammar Datalog;

@header {
package org.clyze.deepdoop.datalog;
}

program
	: (comp | cmd | initialize | propagate | datalog)* ;


lineMarker
	: '#' INTEGER STRING INTEGER* ;

comp
	: COMP IDENTIFIER (':' IDENTIFIER)? L_BRACK datalog* R_BRACK ('as' identifierList)? ;

cmd
	: CMD IDENTIFIER L_BRACK datalog* R_BRACK ('as' identifierList)? ;

initialize
	: IDENTIFIER 'as' identifierList ;

propagate
	: IDENTIFIER '{' (ALL | predicateNameList) '}' '->' (IDENTIFIER | GLOBAL) ;

predicateNameList
	: predicateName
	| predicateNameList ',' predicateName
	;

datalog
	: declaration | constraint | rule_ | lineMarker ;

declaration
	: predicate '->' predicateList? '.'
	| predicateName '(' IDENTIFIER ')' ',' refmode '->' predicate '.'
	;

constraint
	: ruleBody '->' ruleBody '.' ;

rule_
	: predicateList ('<-' ruleBody?)? '.'
	| predicate '<-' aggregation '.'
	;


predicate
	: predicateName (CAPACITY | AT_STAGE)? '(' (exprList | BACKTICK predicateName)? ')'
	| predicateName             AT_STAGE?  '[' (exprList | BACKTICK predicateName)? ']' '=' expr
	| refmode
	;

refmode
	: predicateName AT_STAGE? '(' IDENTIFIER ':' expr ')' ;

ruleBody
	: comparison
	| '!'? predicate
	| '!'? '(' ruleBody ')'
	| ruleBody ',' ruleBody
	| ruleBody ';' ruleBody
	;

aggregation
	: AGG '<<' IDENTIFIER '=' predicate '>>' ruleBody ;

predicateName
	: '$'? IDENTIFIER
	| predicateName ':' IDENTIFIER
	;

constant
	: INTEGER
	| REAL
	| BOOLEAN
	| STRING
	;

expr
	: IDENTIFIER
	| predicateName AT_STAGE? '[' exprList? ']'
	| constant
	| expr ('+' | '-' | '*' | '/') expr
	| '(' expr ')'
	;

comparison
	: expr ('=' | '<' | '<=' | '>' | '>=' | '!=') expr ;

predicateList
	: predicate
	| predicateList ',' predicate
	;

exprList
	: expr
	| exprList ',' expr
	;

identifierList
	: IDENTIFIER
	| identifierList ',' IDENTIFIER
	;


// Lexer

AGG
	: 'agg' ;

ALL
	: '*' ;

AT_STAGE
	: '@init'
	| '@initial'
	| '@prev'
	| '@previous'
	| '@past'
	;

BACKTICK
	: '`' ;

CAPACITY
	: '[' ('32' | '64' | '128') ']' ;

CMD
	: 'command' ;

COMP
	: 'component' ;

GLOBAL
	: '.' ;

L_BRACK
	: '{' ;

R_BRACK
	: '}' ;

INTEGER
	: [0-9]+
	| '0'[0-7]+
	| '0'[xX][0-9a-fA-F]+
	| '2^'[0-9]+
	;

fragment
EXPONENT
	: [eE][-+]?INTEGER ;

REAL
	: INTEGER EXPONENT
	| INTEGER EXPONENT? [fF]
	| (INTEGER)? '.' INTEGER EXPONENT? [fF]?
	;

BOOLEAN
	: 'true' | 'false' ;

STRING
	: '"' ~["]* '"' ;

IDENTIFIER
	: [?]?[a-zA-Z_][a-zA-Z_0-9]* ;


LINE_COMMENT
	: '//' ~[\r\n]* -> skip ;

BLOCK_COMMENT
	: '/*' .*? '*/' -> skip ;

WHITE_SPACE
	: [ \t\r\n]+ -> skip ;
