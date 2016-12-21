# Edigen Maven Plugin

[![Build Status](https://travis-ci.org/sulir/edigen-maven-plugin.png)](https://travis-ci.org/sulir/edigen-maven-plugin)

This is a Maven 3 plugin which automatically generates an instruction decoder and disassembler of an [emuStudio](http://github.com/vbmacher/emuStudio) CPU plugin from a specification using [Edigen](http://github.com/sulir/edigen). Running Edigen manually via the command line is no longer necessary.

## Basic use

Add the plugin to your `pom.xml`:

```xml
<build>
	<plugins>
		<plugin>
			<groupId>com.github.sulir</groupId>
			<artifactId>edigen-maven-plugin</artifactId>
			<version>1.1-SNAPSHOT</version>
			<configuration>
				<decoderName>decoder.package.ClassName</decoderName>
				<disassemblerName>disassembler.package.ClassName</disassemblerName>
			</configuration>
			<executions>
				<execution>
					<goals>
						<goal>generate</goal>
					</goals>
				</execution>
			</executions>
		</plugin>
		...
	</plugins>
</build>
```

Replace `decoder.package.ClassName` with the decoder class and `disassembler.package.ClassName` with the disassembler class to be generated for your emuStudio processor plugin.

Then save your CPU specification file as `src/main/edigen/cpu.eds` and build the project.

## Reference

This plugin offers one goal, `edigen:generate`, which participates in the `generate-sources` lifecycle phase.

### Required parameters

* `decoderName` - the generated instruction decoder package + class name
* `disassemblerName` - the generated disassembler package + class name

### Optional parameters

* `specification` - the specification file location; *default*: `src/main/edigen/cpu.eds`
* `decoderTemplate` - the template to use for the decoder generation
* `decoderOutputDir` - the generated decoder output directory; *default*: `target/generated-sources/edigen/` + path obtained from the decoder package name
* `disassemblerTemplate` - the template to use for the disassembler generation
* `disassemblerOutputDir` - the generated disassembler output directory; *default*: `target/generated-sources/edigen/` + path obtained from the disassembler package name
* `debug` - setting to `true` enables Edigen's debug mode
