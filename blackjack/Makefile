# Create the Blackjack GUI game
SOURCEDIR = src
BUILDDIR = bin

.PHONY: build clean

build: clean
	@echo "*** Compiling source code... ***"
	javac -d $(BUILDDIR) $(SOURCEDIR)/*/*.java $(SOURCEDIR)/*.java
	@echo "*** Copying resources... ***"
	@cp -R $(SOURCEDIR)/bjgame/images $(BUILDDIR)/bjgame
	cp $(SOURCEDIR)/blackjack/META-INF/MANIFEST.MF \
		$(BUILDDIR)/blackjack.mf
	cp $(SOURCEDIR)/bjgame/META-INF/MANIFEST.MF \
		$(BUILDDIR)/bjgame.mf
	@echo "*** Making JAR files... ***"
	cd $(BUILDDIR) && jar cfm blackjack.jar blackjack.mf blackjack
	cd $(BUILDDIR) && jar cfm bjgame.jar bjgame.mf bjgame

clean:
	-rm -rf $(BUILDDIR)
