# Edigen Maven Plugin
![Edigen Maven Plugin Build](https://github.com/emustudio/edigen-maven-plugin/workflows/Edigen%20Maven%20Plugin%20Build/badge.svg)

This is a Maven 3 plugin which automatically generates an instruction decoder and disassembler of
[emuStudio](http://github.com/emustudio/emuStudio) CPU plugin from a specification using
[Edigen](http://github.com/emustudio/edigen).
 
Running Edigen manually via the command line is no longer necessary.

## Usage

Add the plugin to your `pom.xml`:

```xml
<build>
	<plugins>
		<plugin>
			<groupId>net.emustudio</groupId>
			<artifactId>edigen-maven-plugin</artifactId>
			<version>1.3</version>
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
