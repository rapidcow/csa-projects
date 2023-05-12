.PHONY: build

PYTHON   = python3
PANDOC   = pandoc
PDFLATEX = pdflatex
JOBNAME  = swing-intro

build: $(JOBNAME)-content.tex
	$(PDFLATEX) -shell-escape $(JOBNAME)
	$(PDFLATEX) -shell-escape $(JOBNAME)

$(JOBNAME)-content.tex:
	$(PANDOC) --filter ./pandoc_minted.py -o $(JOBNAME)-content.tex $(JOBNAME).md
	$(PYTHON) fix_content.py

clean:
	-rm -rf $(JOBNAME)-content.tex *.out *.aux *.log _minted-*
